package com.example.freeforall.controllers;

import com.example.freeforall.dtos.CreateUserRequest;
import com.example.freeforall.dtos.LoginRequest;
import com.example.freeforall.dtos.UserResponse;
import com.example.freeforall.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.created(URI.create("/api/user/register"))
                .body(UserResponse.fromModel(userService.registerUser(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        String token = userService.signInUser(request);
        return ResponseEntity.ok().body(Map.of("token", token));
    }
}
