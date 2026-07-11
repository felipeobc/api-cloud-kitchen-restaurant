package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuItemRequest {
    private String name;
    private String description;
    private Double price;
    private Boolean onlyInRestaurant;
    private String photoPath;
    private Long restaurantId;
}
