package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.MenuItemManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;
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

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuItemManagementUseCase menuItemManagementUseCase;

    @Test
    void createMenuItem_shouldReturn201() throws Exception {
        MenuItem created = new MenuItem(1L, "Spaghetti Carbonara", "Massa italiana", 45.90, false, "/photos/spaghetti.jpg", 1L);
        when(menuItemManagementUseCase.createMenuItem(any())).thenReturn(created);

        mockMvc.perform(post("/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Spaghetti Carbonara\",\"description\":\"Massa italiana\",\"price\":45.90,\"onlyInRestaurant\":false,\"photoPath\":\"/photos/spaghetti.jpg\",\"restaurantId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Spaghetti Carbonara"))
                .andExpect(jsonPath("$.price").value(45.90));
    }

    @Test
    void getMenuItemById_whenExists_shouldReturn200() throws Exception {
        MenuItem menuItem = new MenuItem(1L, "Spaghetti Carbonara", "Massa italiana", 45.90, false, "/photos/spaghetti.jpg", 1L);
        when(menuItemManagementUseCase.getMenuItemById(1L)).thenReturn(Optional.of(menuItem));

        mockMvc.perform(get("/menu-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Spaghetti Carbonara"));
    }

    @Test
    void getMenuItemById_whenNotExists_shouldReturn404() throws Exception {
        when(menuItemManagementUseCase.getMenuItemById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/menu-items/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllMenuItems_shouldReturn200WithList() throws Exception {
        MenuItem menuItem = new MenuItem(1L, "Spaghetti Carbonara", "Massa italiana", 45.90, false, "/photos/spaghetti.jpg", 1L);
        when(menuItemManagementUseCase.getAllMenuItems()).thenReturn(List.of(menuItem));

        mockMvc.perform(get("/menu-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Spaghetti Carbonara"))
                .andExpect(jsonPath("$[0].price").value(45.90));
    }

    @Test
    void updateMenuItem_shouldReturn200() throws Exception {
        MenuItem updated = new MenuItem(1L, "Spaghetti Atualizado", "Nova descrição", 50.00, true, "/photos/new.jpg", 1L);
        when(menuItemManagementUseCase.updateMenuItem(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/menu-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Spaghetti Atualizado\",\"description\":\"Nova descrição\",\"price\":50.00,\"onlyInRestaurant\":true,\"photoPath\":\"/photos/new.jpg\",\"restaurantId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spaghetti Atualizado"))
                .andExpect(jsonPath("$.price").value(50.00));
    }

    @Test
    void deleteMenuItem_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/menu-items/1"))
                .andExpect(status().isNoContent());
    }
}
