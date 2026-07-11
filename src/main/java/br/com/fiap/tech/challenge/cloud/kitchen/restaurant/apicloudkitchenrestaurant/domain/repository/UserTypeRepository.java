package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTypeRepository {
    UserType save(UserType userType);
    Optional<UserType> findById(UUID id);
    List<UserType> findAll();
    void deleteById(UUID id);
}
