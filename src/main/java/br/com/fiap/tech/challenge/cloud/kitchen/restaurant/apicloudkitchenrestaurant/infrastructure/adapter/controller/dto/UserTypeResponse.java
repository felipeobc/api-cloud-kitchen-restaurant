package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserTypeResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Boolean owner;

    public UserTypeResponse(Long id, String name, String phone, String email, Boolean owner) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
    }

}
