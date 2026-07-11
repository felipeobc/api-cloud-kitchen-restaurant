package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    List<Restaurant> findAll();
    List<Restaurant> findByOwnerId(Long ownerId);
    void deleteById(Long id);
}
