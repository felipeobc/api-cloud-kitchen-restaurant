package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.RestaurantManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.RestaurantRequest;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.RestaurantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantManagementUseCase restaurantManagementUseCase;

    public RestaurantController(RestaurantManagementUseCase restaurantManagementUseCase) {
        this.restaurantManagementUseCase = restaurantManagementUseCase;
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

    private RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getCuisineType(), restaurant.getOpeningHours(), restaurant.getOwnerId());
    }
}
