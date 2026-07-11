package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.infrastructure.persistence;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final MenuItemJpaRepository menuItemJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;

    public MenuItemRepositoryImpl(MenuItemJpaRepository menuItemJpaRepository,
                                  RestaurantJpaRepository restaurantJpaRepository) {
        this.menuItemJpaRepository = menuItemJpaRepository;
        this.restaurantJpaRepository = restaurantJpaRepository;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        RestaurantEntity restaurant = restaurantJpaRepository.findById(menuItem.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + menuItem.getRestaurantId()));
        MenuItemEntity entity = toEntity(menuItem, restaurant);
        MenuItemEntity saved = menuItemJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return menuItemJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItem> findByRestaurantId(Long restaurantId) {
        return menuItemJpaRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        menuItemJpaRepository.deleteById(id);
    }

    private MenuItemEntity toEntity(MenuItem menuItem, RestaurantEntity restaurant) {
        MenuItemEntity entity = new MenuItemEntity();
        if (menuItem.getId() != null) {
            entity.setId(menuItem.getId());
        }
        entity.setName(menuItem.getName());
        entity.setDescription(menuItem.getDescription());
        entity.setPrice(menuItem.getPrice());
        entity.setOnlyInRestaurant(menuItem.getOnlyInRestaurant());
        entity.setPhotoPath(menuItem.getPhotoPath());
        entity.setRestaurant(restaurant);
        return entity;
    }

    private MenuItem toDomain(MenuItemEntity entity) {
        return new MenuItem(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getOnlyInRestaurant(),
                entity.getPhotoPath(),
                entity.getRestaurant().getId()
        );
    }
}
