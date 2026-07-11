package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.MenuItemManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.RestaurantManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.MenuItemResponse;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.RestaurantRequest;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.RestaurantResponse;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.RestaurantWithMenuResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantManagementUseCase restaurantManagementUseCase;
    private final MenuItemManagementUseCase menuItemManagementUseCase;

    public RestaurantController(RestaurantManagementUseCase restaurantManagementUseCase,
                                MenuItemManagementUseCase menuItemManagementUseCase) {
        this.restaurantManagementUseCase = restaurantManagementUseCase;
        this.menuItemManagementUseCase = menuItemManagementUseCase;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody RestaurantRequest request) {
        Restaurant restaurant = new Restaurant(request.getName(), request.getAddress(), request.getCuisineType(), request.getOpeningHours(), request.getOwnerId());
        Restaurant created = restaurantManagementUseCase.createRestaurant(restaurant);
        return new ResponseEntity<>(toResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        return restaurantManagementUseCase.getRestaurantById(id)
                .map(this::toResponse)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantManagementUseCase.getAllRestaurants().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest request) {
        Restaurant restaurant = new Restaurant(id, request.getName(), request.getAddress(), request.getCuisineType(), request.getOpeningHours(), request.getOwnerId());
        Restaurant updated = restaurantManagementUseCase.updateRestaurant(id, restaurant);
        return new ResponseEntity<>(toResponse(updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantManagementUseCase.deleteRestaurant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/with-menu")
    public ResponseEntity<RestaurantWithMenuResponse> getRestaurantWithMenu(@PathVariable Long id) {
        return restaurantManagementUseCase.getRestaurantById(id)
                .map(restaurant -> {
                    List<MenuItemResponse> menuItems = menuItemManagementUseCase.getMenuItemsByRestaurantId(id).stream()
                            .map(this::toMenuItemResponse)
                            .collect(Collectors.toList());
                    RestaurantWithMenuResponse response = new RestaurantWithMenuResponse(
                            restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                            restaurant.getCuisineType(), restaurant.getOpeningHours(),
                            restaurant.getOwnerId(), menuItems);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getCuisineType(), restaurant.getOpeningHours(), restaurant.getOwnerId());
    }

    private MenuItemResponse toMenuItemResponse(MenuItem menuItem) {
        return new MenuItemResponse(menuItem.getId(), menuItem.getName(), menuItem.getDescription(),
                menuItem.getPrice(), menuItem.getOnlyInRestaurant(), menuItem.getPhotoPath(), menuItem.getRestaurantId());
    }
}
