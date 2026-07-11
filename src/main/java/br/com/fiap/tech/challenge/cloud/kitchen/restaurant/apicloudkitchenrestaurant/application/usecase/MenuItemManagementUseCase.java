package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemManagementUseCase {
    MenuItem createMenuItem(MenuItem menuItem);
    Optional<MenuItem> getMenuItemById(Long id);
    List<MenuItem> getAllMenuItems();
    List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId);
    MenuItem updateMenuItem(Long id, MenuItem menuItem);
    void deleteMenuItem(Long id);
}
