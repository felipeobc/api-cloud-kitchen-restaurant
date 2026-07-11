package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RestaurantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createRestaurant_shouldPersistAndReturn201() throws Exception {
        String body = "{\"name\":\"Novo Restaurante\",\"address\":\"Rua Teste, 1\",\"cuisineType\":\"Mexicana\",\"openingHours\":\"Seg-Dom 12:00-22:00\",\"ownerId\":1}";

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Novo Restaurante"))
                .andExpect(jsonPath("$.cuisineType").value("Mexicana"))
                .andExpect(jsonPath("$.ownerId").value(1));
    }

    @Test
    void getAllRestaurants_shouldReturnSeededData() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(3)));
    }

    @Test
    void getRestaurantById_withSeededData_shouldReturn200() throws Exception {
        mockMvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getRestaurantById_withInvalidId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/restaurants/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRestaurant_shouldPersistChanges() throws Exception {
        String updateBody = "{\"name\":\"Cantina Atualizada\",\"address\":\"Av. Nova, 500\",\"cuisineType\":\"Brasileira\",\"openingHours\":\"Seg-Dom 10:00-22:00\",\"ownerId\":1}";

        mockMvc.perform(put("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cantina Atualizada"));

        mockMvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cantina Atualizada"));
    }

    @Test
    void deleteRestaurant_shouldReturn204() throws Exception {
        // Create a new restaurant without menu items and delete it
        String body = "{\"name\":\"Temp\",\"address\":\"Rua X\",\"cuisineType\":\"Italiana\",\"openingHours\":\"Seg-Sex\",\"ownerId\":1}";
        String response = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Integer idInt = com.jayway.jsonpath.JsonPath.read(response, "$.id");
        Long id = idInt.longValue();
        mockMvc.perform(delete("/restaurants/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void getRestaurantWithMenu_shouldReturnRestaurantAndMenuItems() throws Exception {
        mockMvc.perform(get("/restaurants/1/with-menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.menuItems").isArray())
                .andExpect(jsonPath("$.menuItems.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)));
    }

    @Test
    void getRestaurantWithMenu_withInvalidId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/restaurants/9999/with-menu"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRestaurant_withValidOwner_shouldReturn201() throws Exception {
        String body = "{\"name\":\"Restaurante Válido\",\"address\":\"Rua Y\",\"cuisineType\":\"Francesa\",\"openingHours\":\"Seg-Sex\",\"ownerId\":2}";

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ownerId").value(2));
    }
}
