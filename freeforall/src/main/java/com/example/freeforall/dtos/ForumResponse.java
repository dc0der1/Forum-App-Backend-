package com.example.freeforall.dtos;

import com.example.freeforall.entities.ForumEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForumResponse {

    private String postTitle;
    private String postContent;
    private String postCategory;
    private int likeCount;
    private int replyCount;
    private UserResponse userResponse;

    public static ForumResponse fromModel(ForumEntity forumEntity) {
        return new ForumResponse(
                forumEntity.getPostTitle(),
                forumEntity.getPostContent(),
                forumEntity.getCategory(),
                forumEntity.getReplyCount(),
                forumEntity.getLikeCount(),
                UserResponse.fromModel(forumEntity.getUserEntity())
        );
    }
}
