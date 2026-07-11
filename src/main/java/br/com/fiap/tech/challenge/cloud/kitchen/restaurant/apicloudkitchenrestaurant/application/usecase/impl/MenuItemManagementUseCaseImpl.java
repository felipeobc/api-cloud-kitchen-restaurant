package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.impl;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.MenuItemManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemManagementUseCaseImpl implements MenuItemManagementUseCase {

    private final MenuItemRepository menuItemRepository;

    public MenuItemManagementUseCaseImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
