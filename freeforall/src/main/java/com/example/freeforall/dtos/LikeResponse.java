package com.example.freeforall.dtos;

import com.example.freeforall.entities.LikeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeResponse {

    private boolean isLiked;
    private UserResponse userResponse;

    public static LikeResponse fromModel(LikeEntity likeEntity) {
        return new LikeResponse(
                likeEntity.isLiked(),
                UserResponse.fromModel(likeEntity.getUserEntity())
        );
    }
}
