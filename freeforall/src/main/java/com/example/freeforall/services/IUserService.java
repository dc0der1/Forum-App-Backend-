package com.example.freeforall.services;

import com.example.freeforall.dtos.CreateUserRequest;
import com.example.freeforall.dtos.LoginRequest;
import com.example.freeforall.dtos.LoginResponse;
import com.example.freeforall.entities.UserEntity;

public interface IUserService {
    UserEntity registerUser(CreateUserRequest request);
    UserEntity findUserById(String id);
    LoginResponse signInUser(LoginRequest request);
}
