package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserTypeResponse {
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private Boolean owner;

    public UserTypeResponse(UUID id, String name, String phone, String email, Boolean owner) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
    }

}
