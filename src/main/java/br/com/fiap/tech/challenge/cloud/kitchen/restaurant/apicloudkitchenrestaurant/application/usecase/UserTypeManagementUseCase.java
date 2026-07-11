package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTypeManagementUseCase {
    UserType createUserType(UserType userType);
    Optional<UserType> getUserTypeById(UUID id);
    List<UserType> getAllUserTypes();
    UserType updateUserType(UUID id, UserType userType);
    void deleteUserType(UUID id);
}
