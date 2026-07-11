package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.persistence;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.UserTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserTypeRepositoryImpl implements UserTypeRepository {

    private final UserTypeJpaRepository userTypeJpaRepository;

    public UserTypeRepositoryImpl(UserTypeJpaRepository userTypeJpaRepository) {
        this.userTypeJpaRepository = userTypeJpaRepository;
    }

    @Override
    public UserType save(UserType userType) {
        UserTypeEntity userTypeEntity = toEntity(userType);
        UserTypeEntity savedEntity = userTypeJpaRepository.save(userTypeEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<UserType> findById(Long id) { // Changed UUID to Long
        return userTypeJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<UserType> findAll() {
        return userTypeJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) { // Changed UUID to Long
        userTypeJpaRepository.deleteById(id);
    }

    private UserTypeEntity toEntity(UserType userType) {
        // When saving, if userType.getId() is null, it's a new entity, and the DB will generate the ID.
        // If userType.getId() is not null, it's an update.
        return new UserTypeEntity(userType.getId(), userType.getName(), userType.getPhone(), userType.getEmail(), userType.getOwner());
    }

    private UserType toDomain(UserTypeEntity userTypeEntity) {
        return new UserType(userTypeEntity.getId(), userTypeEntity.getName(), userTypeEntity.getPhone(), userTypeEntity.getEmail(), userTypeEntity.getOwner());
    }
}
