package com.example.Shop.service;

import com.example.Shop.dto.UserRequestDTO;
import com.example.Shop.dto.UserResponseDTO;
import com.example.Shop.dto.UserUpdateDTO;
import com.example.Shop.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO request);
    UserResponseDTO getById(Long id);
    List<UserResponseDTO> getAll();
    UserResponseDTO update(Long id, UserUpdateDTO dto);
    void delete(Long id);

    default UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
