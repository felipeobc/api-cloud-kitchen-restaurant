package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.impl;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.UserTypeManagementUseCase;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTypeManagementUseCaseImpl implements UserTypeManagementUseCase {

    private final UserTypeRepository userTypeRepository;

    public UserTypeManagementUseCaseImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public UserType createUserType(UserType userType) {
        return userTypeRepository.save(userType);
    }

    @Override
    public Optional<UserType> getUserTypeById(Long id) {
        return userTypeRepository.findById(id);
    }

    @Override
    public List<UserType> getAllUserTypes() {
        return userTypeRepository.findAll();
    }

    @Override
    public UserType updateUserType(Long id, UserType userType) {
        return userTypeRepository.findById(id)
                .map(existingUserType -> {
                    existingUserType.setName(userType.getName());
                    existingUserType.setPhone(userType.getPhone());
                    existingUserType.setEmail(userType.getEmail());
                    existingUserType.setOwner(userType.getOwner());
                    return userTypeRepository.save(existingUserType);
                })
                .orElseThrow(() -> new RuntimeException("UserType not found with id " + id));
    }

    @Override
    public void deleteUserType(Long id) {
        userTypeRepository.deleteById(id);
    }
}
