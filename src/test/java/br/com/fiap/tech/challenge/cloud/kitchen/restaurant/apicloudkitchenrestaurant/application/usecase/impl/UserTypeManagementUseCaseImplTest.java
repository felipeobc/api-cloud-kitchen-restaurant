package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.application.usecase.impl;

import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity.UserType;
import br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.repository.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTypeManagementUseCaseImplTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeManagementUseCaseImpl useCase;

    private UserType userType;

    @BeforeEach
    void setUp() {
        userType = new UserType(1L, "Fernando Almeida", "11987654321", "fernando@example.com", true);
    }

    @Test
    void createUserType_shouldSaveAndReturnUserType() {
        UserType newUser = new UserType("Fernando Almeida", "11987654321", "fernando@example.com", true);
        when(userTypeRepository.save(newUser)).thenReturn(userType);

        UserType result = useCase.createUserType(newUser);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Fernando Almeida");
        verify(userTypeRepository).save(newUser);
    }

    @Test
    void getUserTypeById_whenExists_shouldReturnUserType() {
        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        Optional<UserType> result = useCase.getUserTypeById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("fernando@example.com");
    }

    @Test
    void getUserTypeById_whenNotExists_shouldReturnEmpty() {
        when(userTypeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserType> result = useCase.getUserTypeById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void getAllUserTypes_shouldReturnList() {
        when(userTypeRepository.findAll()).thenReturn(List.of(userType));

        List<UserType> result = useCase.getAllUserTypes();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Fernando Almeida");
    }

    @Test
    void updateUserType_shouldSaveAndReturnUpdated() {
        UserType updated = new UserType(1L, "Fernando Atualizado", "11999999999", "novo@example.com", true);
        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));
        when(userTypeRepository.save(any())).thenReturn(updated);

        UserType result = useCase.updateUserType(1L, updated);

        assertThat(result.getName()).isEqualTo("Fernando Atualizado");
        verify(userTypeRepository).save(any());
    }

    @Test
    void deleteUserType_shouldCallRepository() {
        doNothing().when(userTypeRepository).deleteById(1L);

        useCase.deleteUserType(1L);

        verify(userTypeRepository).deleteById(1L);
    }
}
