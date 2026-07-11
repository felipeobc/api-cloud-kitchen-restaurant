package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.impl;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.MenuItem;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.MenuItemRepository;
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
class MenuItemManagementUseCaseImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemManagementUseCaseImpl useCase;

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem(1L, "Spaghetti Carbonara", "Massa italiana", 45.90, false, "/photos/spaghetti.jpg", 1L);
    }

    @Test
    void createMenuItem_shouldSaveAndReturn() {
        MenuItem newItem = new MenuItem("Spaghetti Carbonara", "Massa italiana", 45.90, false, "/photos/spaghetti.jpg", 1L);
        when(menuItemRepository.save(newItem)).thenReturn(menuItem);

        MenuItem result = useCase.createMenuItem(newItem);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Spaghetti Carbonara");
        verify(menuItemRepository).save(newItem);
    }

    @Test
    void getMenuItemById_whenExists_shouldReturn() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        Optional<MenuItem> result = useCase.getMenuItemById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualTo(45.90);
    }

    @Test
    void getMenuItemById_whenNotExists_shouldReturnEmpty() {
        when(menuItemRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<MenuItem> result = useCase.getMenuItemById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void getAllMenuItems_shouldReturnList() {
        when(menuItemRepository.findAll()).thenReturn(List.of(menuItem));

        List<MenuItem> result = useCase.getAllMenuItems();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Spaghetti Carbonara");
    }

    @Test
    void getMenuItemsByRestaurantId_shouldReturnList() {
        when(menuItemRepository.findByRestaurantId(1L)).thenReturn(List.of(menuItem));

        List<MenuItem> result = useCase.getMenuItemsByRestaurantId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRestaurantId()).isEqualTo(1L);
    }

    @Test
    void updateMenuItem_shouldSaveAndReturn() {
        MenuItem updated = new MenuItem(1L, "Spaghetti Atualizado", "Nova descrição", 50.00, true, "/photos/new.jpg", 1L);
        when(menuItemRepository.save(updated)).thenReturn(updated);

        MenuItem result = useCase.updateMenuItem(1L, updated);

        assertThat(result.getName()).isEqualTo("Spaghetti Atualizado");
        verify(menuItemRepository).save(updated);
    }

    @Test
    void deleteMenuItem_shouldCallRepository() {
        doNothing().when(menuItemRepository).deleteById(1L);

        useCase.deleteMenuItem(1L);

        verify(menuItemRepository).deleteById(1L);
    }
}
