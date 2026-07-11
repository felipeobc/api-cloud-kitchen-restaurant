package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean onlyInRestaurant;
    private String photoPath;
    private Long restaurantId;

    public MenuItem(String name, String description, Double price, Boolean onlyInRestaurant, String photoPath, Long restaurantId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.onlyInRestaurant = onlyInRestaurant;
        this.photoPath = photoPath;
        this.restaurantId = restaurantId;
    }

    public MenuItem(Long id, String name, String description, Double price, Boolean onlyInRestaurant, String photoPath, Long restaurantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.onlyInRestaurant = onlyInRestaurant;
        this.photoPath = photoPath;
        this.restaurantId = restaurantId;
    }
}
