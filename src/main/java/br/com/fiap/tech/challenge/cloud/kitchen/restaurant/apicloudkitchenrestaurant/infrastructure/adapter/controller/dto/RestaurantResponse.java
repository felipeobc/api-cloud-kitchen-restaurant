package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private Long ownerId;

    public RestaurantResponse(Long id, String name, String address, String cuisineType, String openingHours, Long ownerId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerId = ownerId;
    }
}
