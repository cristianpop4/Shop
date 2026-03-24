package com.example.Shop.service.serviceimpl;

import com.example.Shop.dto.UserRequestDTO;
import com.example.Shop.dto.UserResponseDTO;
import com.example.Shop.dto.UserUpdateDTO;
import com.example.Shop.entity.User;
import com.example.Shop.exceptions.EmailAlreadyExistsException;
import com.example.Shop.exceptions.ResourceNotFoundException;
import com.example.Shop.repository.UserRepository;
import com.example.Shop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final MailService mailService;

    public UserServiceImpl(UserRepository repository, MailService mailService) {
        this.repository = repository;
        this.mailService = mailService;
    }

    public UserResponseDTO createUser(UserRequestDTO request) {

        repository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException(u.getEmail());
                });

        User user = new User(
                null,
                request.name(),
                request.email(),
                null
        );

        mailService.sendWelcomeEmail(
                user.getUsername(),
                user.getEmail()
        );

        return toDto(repository.save(user));
    }

    public UserResponseDTO getById(Long id) {
        return toDto(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("user not found"))
        );
    }

    public List<UserResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user not found"));

        user.setUsername(dto.name());
        user.setEmail(dto.email());

        return toDto(repository.save(user));
    }

    public void delete(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user not found"));

        repository.delete(user);
    }
}
