package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class RestaurantWithMenuResponse {
    private Long id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private Long ownerId;
    private List<MenuItemResponse> menuItems;
}
