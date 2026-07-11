package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Long> {
    List<RestaurantEntity> findByOwnerId(Long ownerId);
}
