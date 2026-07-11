package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "user_types")
public class UserTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private Boolean owner;

    public UserTypeEntity() {
    }

    public UserTypeEntity(UUID id, String name, String phone, String email, Boolean owner) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
    }

}
