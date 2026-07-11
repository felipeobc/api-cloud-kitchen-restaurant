package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.persistence;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final UserTypeJpaRepository userTypeJpaRepository;

    public RestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository,
                                    UserTypeJpaRepository userTypeJpaRepository) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.userTypeJpaRepository = userTypeJpaRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        UserTypeEntity owner = userTypeJpaRepository.findById(restaurant.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + restaurant.getOwnerId()));
        RestaurantEntity entity = toEntity(restaurant, owner);
        RestaurantEntity saved = restaurantJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return restaurantJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> findByOwnerId(Long ownerId) {
        return restaurantJpaRepository.findByOwnerId(ownerId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        restaurantJpaRepository.deleteById(id);
    }

    private RestaurantEntity toEntity(Restaurant restaurant, UserTypeEntity owner) {
        RestaurantEntity entity = new RestaurantEntity();
        if (restaurant.getId() != null) {
            entity.setId(restaurant.getId());
        }
        entity.setName(restaurant.getName());
        entity.setAddress(restaurant.getAddress());
        entity.setCuisineType(restaurant.getCuisineType());
        entity.setOpeningHours(restaurant.getOpeningHours());
        entity.setOwner(owner);
        return entity;
    }

    private Restaurant toDomain(RestaurantEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCuisineType(),
                entity.getOpeningHours(),
                entity.getOwner().getId()
        );
    }
}
