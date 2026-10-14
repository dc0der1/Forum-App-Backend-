package com.example.freeforall.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.freeforall.dtos.CreateUserRequest;
import com.example.freeforall.entities.Role;
import com.example.freeforall.entities.UserEntity;
import com.example.freeforall.exceptions.UserAlreadyExistsException;
import com.example.freeforall.exceptions.UserNotFoundException;
import com.example.freeforall.repositories.IUserRepository;
import com.example.freeforall.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService{

    private final IUserRepository userRepository;

    @Override
    public UserEntity registerUser(CreateUserRequest request) {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email: " + request.getEmail() + " already exists.");
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, request.getPassword().toCharArray());

        UserEntity user = new UserEntity(request.getUsername(), request.getEmail(), hashedPassword, Role.USER);

        userRepository.save(user);
        log.info("User {} has been created", user.getUsername());

        return user;
    }

    @Override
    public UserEntity findUserById(String id) {
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(id));

        if (user.isEmpty()) {
            throw new UserNotFoundException("No user found with id: " + id);
        }

        if (!ValidationUtils.isValidUUID(id)) {
            throw new IllegalArgumentException("Invalid user id: " + id);
        }

        return user.get();
    }
}
