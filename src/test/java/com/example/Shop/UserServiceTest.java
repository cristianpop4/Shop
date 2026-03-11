package com.example.Shop;

import com.example.Shop.dto.UserRequestDTO;
import com.example.Shop.dto.UserResponseDTO;
import com.example.Shop.dto.UserUpdateDTO;
import com.example.Shop.entity.User;
import com.example.Shop.exceptions.EmailAlreadyExistsException;
import com.example.Shop.exceptions.ResourceNotFoundException;
import com.example.Shop.repository.UserRepository;
import com.example.Shop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO request;

    @BeforeEach
    void setup(){

        user = new User(
                1L,
                "marcel",
                "marcel@gmail.com",
                null
        );

        request = new UserRequestDTO(
                "marcel",
                "marcel@gmail.com"
        );
    }

    @Test
    void createUser_ShouldSaveUser(){

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        when(repository.save(any(User.class)))
                .thenReturn(user);

        UserResponseDTO response = userService.createUser(request);

        assertEquals("marcel", response.name());

        verify(repository).save(any(User.class));
    }

    @Test
    void createUser_ShouldTrowException_WhenEmailExists(){

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> userService.createUser(request)
        );

        verify(repository, never()).save(any());
    }

    @Test
    void getByid_ShouldReturnUser(){

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponseDTO responseDTO = userService.getById(1L);

        assertEquals("marcel", responseDTO.name());
        assertEquals("marcel@gmail.com", responseDTO.email());
    }

    @Test
    void getById_ShouldThrowException_WhenMissing(){

        when(repository.findById(2L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getById(2L)
        );
    }

    @Test
    void getAll_ShouldReturnUsers(){

        when(repository.findAll())
                .thenReturn(List.of(user));

        List<UserResponseDTO> users = userService.getAll();

        assertEquals(1, users.size());
        assertEquals("marcel", users.getFirst().name());

        verify(repository).findAll();
    }

    @Test
    void update_ShouldModifyUser(){

        UserUpdateDTO updateDTO =
                new UserUpdateDTO("updated", "updated@gmail.com");

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));

        when(repository.save(user))
                .thenReturn(user);

        UserResponseDTO responseDTO =
                userService.update(1L, updateDTO);

        assertEquals("updated", responseDTO.name());
        assertEquals("updated@gmail.com", responseDTO.email());

        verify(repository).save(user);
    }

    @Test
    void delete_ShouldRemoveUser(){

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));

        doNothing().when(repository).delete(user);

        userService.delete(1L);

        verify(repository).delete(user);
    }
}