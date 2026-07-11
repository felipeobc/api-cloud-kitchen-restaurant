package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.MenuItemManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.MenuItemRequest;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.MenuItemResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemManagementUseCase menuItemManagementUseCase;

    public MenuItemController(MenuItemManagementUseCase menuItemManagementUseCase) {
        this.menuItemManagementUseCase = menuItemManagementUseCase;
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(@RequestBody MenuItemRequest request) {
        MenuItem menuItem = new MenuItem(request.getName(), request.getDescription(), request.getPrice(),
                request.getOnlyInRestaurant(), request.getPhotoPath(), request.getRestaurantId());
        MenuItem created = menuItemManagementUseCase.createMenuItem(menuItem);
        return new ResponseEntity<>(toResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id) {
        return menuItemManagementUseCase.getMenuItemById(id)
                .map(this::toResponse)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems() {
        List<MenuItemResponse> items = menuItemManagementUseCase.getAllMenuItems().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequest request) {
        MenuItem menuItem = new MenuItem(id, request.getName(), request.getDescription(), request.getPrice(),
                request.getOnlyInRestaurant(), request.getPhotoPath(), request.getRestaurantId());
        MenuItem updated = menuItemManagementUseCase.updateMenuItem(id, menuItem);
        return new ResponseEntity<>(toResponse(updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemManagementUseCase.deleteMenuItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private MenuItemResponse toResponse(MenuItem menuItem) {
        return new MenuItemResponse(menuItem.getId(), menuItem.getName(), menuItem.getDescription(),
                menuItem.getPrice(), menuItem.getOnlyInRestaurant(), menuItem.getPhotoPath(), menuItem.getRestaurantId());
    }
}
