package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantRequest {
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private Long ownerId;
}
