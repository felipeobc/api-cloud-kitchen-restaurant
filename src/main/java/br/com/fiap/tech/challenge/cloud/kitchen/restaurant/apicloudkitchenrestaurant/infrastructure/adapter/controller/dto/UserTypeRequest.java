package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserTypeRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Boolean owner;

}
