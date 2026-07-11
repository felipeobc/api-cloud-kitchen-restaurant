package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantManagementUseCase {
    Restaurant createRestaurant(Restaurant restaurant);
    Optional<Restaurant> getRestaurantById(Long id);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByOwnerId(Long ownerId);
    Restaurant updateRestaurant(Long id, Restaurant restaurant);
    void deleteRestaurant(Long id);
}
