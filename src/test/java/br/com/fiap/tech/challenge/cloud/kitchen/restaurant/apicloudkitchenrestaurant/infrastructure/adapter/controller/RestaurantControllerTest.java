package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.MenuItemManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.RestaurantManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;
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

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantManagementUseCase restaurantManagementUseCase;

    @MockitoBean
    private MenuItemManagementUseCase menuItemManagementUseCase;

    @Test
    void createRestaurant_shouldReturn201() throws Exception {
        Restaurant created = new Restaurant(1L, "Cantina do Fernando", "Rua das Flores, 123", "Italiana", "Seg-Sex 11:00-23:00", 1L);
        when(restaurantManagementUseCase.createRestaurant(any())).thenReturn(created);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Cantina do Fernando\",\"address\":\"Rua das Flores, 123\",\"cuisineType\":\"Italiana\",\"openingHours\":\"Seg-Sex 11:00-23:00\",\"ownerId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cantina do Fernando"))
                .andExpect(jsonPath("$.cuisineType").value("Italiana"));
    }

    @Test
    void getRestaurantById_whenExists_shouldReturn200() throws Exception {
        Restaurant restaurant = new Restaurant(1L, "Cantina do Fernando", "Rua das Flores, 123", "Italiana", "Seg-Sex 11:00-23:00", 1L);
        when(restaurantManagementUseCase.getRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        mockMvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cantina do Fernando"));
    }

    @Test
    void getRestaurantById_whenNotExists_shouldReturn404() throws Exception {
        when(restaurantManagementUseCase.getRestaurantById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/restaurants/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRestaurants_shouldReturn200WithList() throws Exception {
        Restaurant restaurant = new Restaurant(1L, "Cantina do Fernando", "Rua das Flores, 123", "Italiana", "Seg-Sex 11:00-23:00", 1L);
        when(restaurantManagementUseCase.getAllRestaurants()).thenReturn(List.of(restaurant));

        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cantina do Fernando"));
    }

    @Test
    void updateRestaurant_shouldReturn200() throws Exception {
        Restaurant updated = new Restaurant(1L, "Cantina Atualizada", "Av. Paulista, 1000", "Brasileira", "Seg-Dom 10:00-22:00", 1L);
        when(restaurantManagementUseCase.updateRestaurant(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Cantina Atualizada\",\"address\":\"Av. Paulista, 1000\",\"cuisineType\":\"Brasileira\",\"openingHours\":\"Seg-Dom 10:00-22:00\",\"ownerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cantina Atualizada"));
    }

    @Test
    void deleteRestaurant_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/restaurants/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getRestaurantWithMenu_whenExists_shouldReturn200WithMenuItems() throws Exception {
        Restaurant restaurant = new Restaurant(1L, "Cantina do Fernando", "Rua das Flores, 123", "Italiana", "Seg-Sex 11:00-23:00", 1L);
        MenuItem menuItem = new MenuItem(1L, "Spaghetti Carbonara", "Massa italiana", 45.90, false, "/photos/spaghetti.jpg", 1L);
        when(restaurantManagementUseCase.getRestaurantById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemManagementUseCase.getMenuItemsByRestaurantId(1L)).thenReturn(List.of(menuItem));

        mockMvc.perform(get("/restaurants/1/with-menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cantina do Fernando"))
                .andExpect(jsonPath("$.menuItems[0].name").value("Spaghetti Carbonara"));
    }

    @Test
    void getRestaurantWithMenu_whenNotExists_shouldReturn404() throws Exception {
        when(restaurantManagementUseCase.getRestaurantById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/restaurants/99/with-menu"))
                .andExpect(status().isNotFound());
    }
}
