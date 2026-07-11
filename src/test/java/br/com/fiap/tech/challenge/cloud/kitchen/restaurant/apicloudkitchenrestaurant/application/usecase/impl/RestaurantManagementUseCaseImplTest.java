package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.impl;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.Restaurant;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantManagementUseCaseImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantManagementUseCaseImpl useCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1L, "Cantina do Fernando", "Rua das Flores, 123", "Italiana", "Seg-Sex 11:00-23:00", 1L);
    }

    @Test
    void createRestaurant_shouldSaveAndReturn() {
        Restaurant newRestaurant = new Restaurant("Cantina do Fernando", "Rua das Flores, 123", "Italiana", "Seg-Sex 11:00-23:00", 1L);
        when(restaurantRepository.save(newRestaurant)).thenReturn(restaurant);

        Restaurant result = useCase.createRestaurant(newRestaurant);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Cantina do Fernando");
        verify(restaurantRepository).save(newRestaurant);
    }

    @Test
    void getRestaurantById_whenExists_shouldReturn() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        Optional<Restaurant> result = useCase.getRestaurantById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getCuisineType()).isEqualTo("Italiana");
    }

    @Test
    void getRestaurantById_whenNotExists_shouldReturnEmpty() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Restaurant> result = useCase.getRestaurantById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void getAllRestaurants_shouldReturnList() {
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));

        List<Restaurant> result = useCase.getAllRestaurants();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Cantina do Fernando");
    }

    @Test
    void getRestaurantsByOwnerId_shouldReturnList() {
        when(restaurantRepository.findByOwnerId(1L)).thenReturn(List.of(restaurant));

        List<Restaurant> result = useCase.getRestaurantsByOwnerId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOwnerId()).isEqualTo(1L);
    }

    @Test
    void updateRestaurant_shouldSaveAndReturn() {
        Restaurant updated = new Restaurant(1L, "Cantina Atualizada", "Av. Paulista, 1000", "Brasileira", "Seg-Dom 10:00-22:00", 1L);
        when(restaurantRepository.save(updated)).thenReturn(updated);

        Restaurant result = useCase.updateRestaurant(1L, updated);

        assertThat(result.getName()).isEqualTo("Cantina Atualizada");
        verify(restaurantRepository).save(updated);
    }

    @Test
    void deleteRestaurant_shouldCallRepository() {
        doNothing().when(restaurantRepository).deleteById(1L);

        useCase.deleteRestaurant(1L);

        verify(restaurantRepository).deleteById(1L);
    }
}
