package com.example.freeforall.dtos;

import com.example.freeforall.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private UUID userId;
    private String username;
    private Date createdAt;

    public static UserResponse fromModel(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getCreatedAt()
        );
    }
}
