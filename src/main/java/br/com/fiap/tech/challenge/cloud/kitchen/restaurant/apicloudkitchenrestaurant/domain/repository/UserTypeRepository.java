package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;

import java.util.List;
import java.util.Optional;

public interface UserTypeRepository {
    UserType save(UserType userType);
    Optional<UserType> findById(Long id);
    List<UserType> findAll();
    void deleteById(Long id);
}
