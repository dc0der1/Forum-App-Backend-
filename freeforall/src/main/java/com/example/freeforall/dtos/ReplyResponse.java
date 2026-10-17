package com.example.freeforall.dtos;

import com.example.freeforall.entities.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ReplyResponse {
    private final UUID replyId;
    private String reply;
    private Date createdAt;
    private final UUID forumId;
    private UserResponse userResponse;

    public static ReplyResponse fromModel(ReplyEntity replyEntity) {
        return new ReplyResponse(
                replyEntity.getId(),
                replyEntity.getReply(),
                replyEntity.getCreatedAt(),
                replyEntity.getForumEntity() != null ? replyEntity.getForumEntity().getId() : null,
                UserResponse.fromModel(replyEntity.getUserEntity())
        );
    }
}
