package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserType {
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private Boolean owner;

    public UserType(String name, String phone, String email, Boolean owner) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
    }

    public UserType(UUID id, String name, String phone, String email, Boolean owner) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
    }

}