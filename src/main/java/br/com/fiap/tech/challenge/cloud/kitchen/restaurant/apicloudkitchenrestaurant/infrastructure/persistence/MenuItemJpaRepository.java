package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByRestaurantId(Long restaurantId);
}
