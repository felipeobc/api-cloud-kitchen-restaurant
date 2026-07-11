package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.RestaurantManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.UserTypeManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserTypeController.class)
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserTypeManagementUseCase userTypeManagementUseCase;

    @MockitoBean
    private RestaurantManagementUseCase restaurantManagementUseCase;

    @Test
    void createUserType_shouldReturn201() throws Exception {
        UserType created = new UserType(1L, "Fernando Almeida", "11987654321", "fernando@example.com", true);
        when(userTypeManagementUseCase.createUserType(any())).thenReturn(created);

        mockMvc.perform(post("/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fernando Almeida\",\"phone\":\"11987654321\",\"email\":\"fernando@example.com\",\"owner\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fernando Almeida"))
                .andExpect(jsonPath("$.owner").value(true));
    }

    @Test
    void getUserTypeById_whenExists_shouldReturn200() throws Exception {
        UserType userType = new UserType(1L, "Fernando Almeida", "11987654321", "fernando@example.com", true);
        when(userTypeManagementUseCase.getUserTypeById(1L)).thenReturn(Optional.of(userType));

        mockMvc.perform(get("/user-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fernando Almeida"));
    }

    @Test
    void getUserTypeById_whenNotExists_shouldReturn404() throws Exception {
        when(userTypeManagementUseCase.getUserTypeById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user-types/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUserTypes_shouldReturn200WithList() throws Exception {
        UserType userType = new UserType(1L, "Fernando Almeida", "11987654321", "fernando@example.com", true);
        when(userTypeManagementUseCase.getAllUserTypes()).thenReturn(List.of(userType));

        mockMvc.perform(get("/user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fernando Almeida"));
    }

    @Test
    void updateUserType_shouldReturn200() throws Exception {
        UserType updated = new UserType(1L, "Fernando Atualizado", "11999999999", "novo@example.com", true);
        when(userTypeManagementUseCase.updateUserType(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/user-types/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fernando Atualizado\",\"phone\":\"11999999999\",\"email\":\"novo@example.com\",\"owner\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fernando Atualizado"));
    }

    @Test
    void deleteUserType_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/user-types/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUserTypeWithRestaurants_ownerWithRestaurants_shouldReturn200() throws Exception {
        UserType owner = new UserType(1L, "Fernando Almeida", "11987654321", "fernando@example.com", true);
        Restaurant restaurant = new Restaurant(1L, "Cantina do Fernando", "Rua das Flores, 123", "Italiana", "Seg-Sex 11:00-23:00", 1L);
        when(userTypeManagementUseCase.getUserTypeById(1L)).thenReturn(Optional.of(owner));
        when(restaurantManagementUseCase.getRestaurantsByOwnerId(1L)).thenReturn(List.of(restaurant));

        mockMvc.perform(get("/user-types/1/with-restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.owner").value(true))
                .andExpect(jsonPath("$.restaurants[0].name").value("Cantina do Fernando"));
    }

    @Test
    void getUserTypeWithRestaurants_nonOwner_shouldReturnEmptyRestaurants() throws Exception {
        UserType client = new UserType(2L, "Ana Silva", "11912345671", "ana@example.com", false);
        when(userTypeManagementUseCase.getUserTypeById(2L)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/user-types/2/with-restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value(false))
                .andExpect(jsonPath("$.restaurants").isEmpty());
    }

    @Test
    void getUserTypeWithRestaurants_whenNotExists_shouldReturn404() throws Exception {
        when(userTypeManagementUseCase.getUserTypeById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user-types/99/with-restaurants"))
                .andExpect(status().isNotFound());
    }
}
