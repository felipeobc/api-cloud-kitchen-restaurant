package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.RestaurantManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.UserTypeManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.RestaurantResponse;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.UserTypeRequest;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.UserTypeResponse;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.UserTypeWithRestaurantsResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-types")
public class UserTypeController {

    private final UserTypeManagementUseCase userTypeManagementUseCase;
    private final RestaurantManagementUseCase restaurantManagementUseCase;

    public UserTypeController(UserTypeManagementUseCase userTypeManagementUseCase,
                              RestaurantManagementUseCase restaurantManagementUseCase) {
        this.userTypeManagementUseCase = userTypeManagementUseCase;
        this.restaurantManagementUseCase = restaurantManagementUseCase;
    }

    @PostMapping
    public ResponseEntity<UserTypeResponse> createUserType(@Valid @RequestBody UserTypeRequest request) {
        UserType userType = new UserType(request.getName(), request.getPhone(), request.getEmail(), request.getOwner());
        UserType createdUserType = userTypeManagementUseCase.createUserType(userType);
        return new ResponseEntity<>(toResponse(createdUserType), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponse> getUserTypeById(@PathVariable Long id) {
        return userTypeManagementUseCase.getUserTypeById(id)
                .map(this::toResponse)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<UserTypeResponse>> getAllUserTypes() {
        List<UserTypeResponse> userTypes = userTypeManagementUseCase.getAllUserTypes().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userTypes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTypeResponse> updateUserType(@PathVariable Long id, @Valid @RequestBody UserTypeRequest request) {
        UserType userType = new UserType(id, request.getName(), request.getPhone(), request.getEmail(), request.getOwner());
        UserType updatedUserType = userTypeManagementUseCase.updateUserType(id, userType);
        return new ResponseEntity<>(toResponse(updatedUserType), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/with-restaurants")
    public ResponseEntity<UserTypeWithRestaurantsResponse> getUserTypeWithRestaurants(@PathVariable Long id) {
        return userTypeManagementUseCase.getUserTypeById(id)
                .map(userType -> {
                    List<RestaurantResponse> restaurants = userType.getOwner()
                            ? restaurantManagementUseCase.getRestaurantsByOwnerId(id).stream()
                                    .map(this::toRestaurantResponse)
                                    .collect(Collectors.toList())
                            : List.of();
                    UserTypeWithRestaurantsResponse response = new UserTypeWithRestaurantsResponse(
                            userType.getId(), userType.getName(), userType.getPhone(),
                            userType.getEmail(), userType.getOwner(), restaurants);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private UserTypeResponse toResponse(UserType userType) {
        return new UserTypeResponse(userType.getId(), userType.getName(), userType.getPhone(), userType.getEmail(), userType.getOwner());
    }

    private RestaurantResponse toRestaurantResponse(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(), restaurant.getOwnerId());
    }
}
