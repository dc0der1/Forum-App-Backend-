package com.example.freeforall.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.freeforall.dtos.CreateUserRequest;
import com.example.freeforall.dtos.LoginRequest;
import com.example.freeforall.dtos.LoginResponse;
import com.example.freeforall.entities.Role;
import com.example.freeforall.entities.UserEntity;
import com.example.freeforall.exceptions.user.InvalidLoginException;
import com.example.freeforall.exceptions.user.UserAlreadyExistsException;
import com.example.freeforall.exceptions.user.UserNotFoundException;
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
    private final IJwtService jwtService;

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

    @Override
    public LoginResponse signInUser(LoginRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findUserByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new InvalidLoginException("Invalid email or password");
        }

        UserEntity user = optionalUser.get();

        assert user.getPassword() != null;
        BCrypt.Result result = BCrypt.verifyer().verify(
                request.getPassword().toCharArray(),
                user.getPassword()
        );

        if (!result.verified) {
            throw new InvalidLoginException("Invalid email or password");
        }

        return new LoginResponse(jwtService.generateToken(user), user.getId());
    }
}
