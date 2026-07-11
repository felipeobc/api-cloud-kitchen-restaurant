package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private Long ownerId;

    public Restaurant(String name, String address, String cuisineType, String openingHours, Long ownerId) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerId = ownerId;
    }

    public Restaurant(Long id, String name, String address, String cuisineType, String openingHours, Long ownerId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerId = ownerId;
    }
}
