package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);
    Optional<MenuItem> findById(Long id);
    List<MenuItem> findAll();
    List<MenuItem> findByRestaurantId(Long restaurantId);
    void deleteById(Long id);
}
