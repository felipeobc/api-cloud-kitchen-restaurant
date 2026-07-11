package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.UserTypeManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.UserTypeRequest;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.adapter.controller.dto.UserTypeResponse;
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

    public UserTypeController(UserTypeManagementUseCase userTypeManagementUseCase) {
        this.userTypeManagementUseCase = userTypeManagementUseCase;
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

    private UserTypeResponse toResponse(UserType userType) {
        return new UserTypeResponse(userType.getId(), userType.getName(), userType.getPhone(), userType.getEmail(), userType.getOwner());
    }
}
