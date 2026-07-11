package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MenuItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createMenuItem_shouldPersistAndReturn201() throws Exception {
        String body = "{\"name\":\"Bruschetta\",\"description\":\"Pão italiano com tomate\",\"price\":18.50,\"onlyInRestaurant\":false,\"photoPath\":\"/photos/bruschetta.jpg\",\"restaurantId\":1}";

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Bruschetta"))
                .andExpect(jsonPath("$.price").value(18.50))
                .andExpect(jsonPath("$.restaurantId").value(1));
    }

    @Test
    void getAllMenuItems_shouldReturnSeededData() throws Exception {
        mockMvc.perform(get("/menu-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(14)));
    }

    @Test
    void getMenuItemById_withSeededData_shouldReturn200() throws Exception {
        mockMvc.perform(get("/menu-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void getMenuItemById_withInvalidId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/menu-items/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMenuItem_shouldPersistChanges() throws Exception {
        String updateBody = "{\"name\":\"Spaghetti Atualizado\",\"description\":\"Nova descrição\",\"price\":55.00,\"onlyInRestaurant\":true,\"photoPath\":\"/photos/new.jpg\",\"restaurantId\":1}";

        mockMvc.perform(put("/menu-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spaghetti Atualizado"))
                .andExpect(jsonPath("$.price").value(55.00));

        mockMvc.perform(get("/menu-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spaghetti Atualizado"));
    }

    @Test
    void deleteMenuItem_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/menu-items/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createMenuItem_withValidRestaurant_shouldReturn201() throws Exception {
        String body = "{\"name\":\"Item Válido\",\"description\":\"Desc válida\",\"price\":25.00,\"onlyInRestaurant\":true,\"photoPath\":\"/photos/valid.jpg\",\"restaurantId\":2}";

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Item Válido"))
                .andExpect(jsonPath("$.restaurantId").value(2));
    }
}
