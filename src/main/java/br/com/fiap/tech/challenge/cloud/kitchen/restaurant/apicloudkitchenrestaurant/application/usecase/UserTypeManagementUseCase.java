package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;

import java.util.List;
import java.util.Optional;

public interface UserTypeManagementUseCase {
    UserType createUserType(UserType userType);
    Optional<UserType> getUserTypeById(Long id);
    List<UserType> getAllUserTypes();
    UserType updateUserType(Long id, UserType userType);
    void deleteUserType(Long id);
}
