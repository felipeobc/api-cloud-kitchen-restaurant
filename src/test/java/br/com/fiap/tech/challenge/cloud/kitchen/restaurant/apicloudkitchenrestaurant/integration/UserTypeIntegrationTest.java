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
class UserTypeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndGetUserType_shouldPersistAndReturn() throws Exception {
        String body = "{\"name\":\"Teste Integração\",\"phone\":\"11999990000\",\"email\":\"integracao@test.com\",\"owner\":true}";

        String location = mockMvc.perform(post("/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Teste Integração"))
                .andExpect(jsonPath("$.owner").value(true))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllUserTypes_shouldReturnSeededData() throws Exception {
        mockMvc.perform(get("/user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(8)));
    }

    @Test
    void getUserTypeById_withSeededOwner_shouldReturn200() throws Exception {
        mockMvc.perform(get("/user-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.owner").value(true));
    }

    @Test
    void getUserTypeById_withInvalidId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/user-types/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserType_shouldPersistChanges() throws Exception {
        String updateBody = "{\"name\":\"Fernando Atualizado\",\"phone\":\"11000000000\",\"email\":\"atualizado@test.com\",\"owner\":true}";

        mockMvc.perform(put("/user-types/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fernando Atualizado"));

        mockMvc.perform(get("/user-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fernando Atualizado"));
    }

    @Test
    void deleteUserType_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/user-types/8"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUserTypeWithRestaurants_ownerShouldReturnRestaurants() throws Exception {
        mockMvc.perform(get("/user-types/1/with-restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value(true))
                .andExpect(jsonPath("$.restaurants").isArray())
                .andExpect(jsonPath("$.restaurants.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)));
    }

    @Test
    void getUserTypeWithRestaurants_clientShouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/user-types/4/with-restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value(false))
                .andExpect(jsonPath("$.restaurants").isEmpty());
    }
}
