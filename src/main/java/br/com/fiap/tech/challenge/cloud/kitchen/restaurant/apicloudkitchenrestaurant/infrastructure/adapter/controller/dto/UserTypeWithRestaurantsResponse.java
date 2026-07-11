package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UserTypeWithRestaurantsResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Boolean owner;
    private List<RestaurantResponse> restaurants;
}
