package com.example.freeforall.services;

import com.example.freeforall.entities.UserEntity;

import java.util.UUID;

public interface IJwtService {
    String generateToken(UserEntity userEntity);
    UUID validateToken(String token);
}
