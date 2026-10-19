package com.example.freeforall.services;

import com.example.freeforall.dtos.CreateForumRequest;
import com.example.freeforall.dtos.CreateReplyRequest;
import com.example.freeforall.entities.ForumEntity;
import com.example.freeforall.entities.LikeEntity;
import com.example.freeforall.entities.ReplyEntity;

import java.util.UUID;

public interface IForumService {
    ForumEntity createForum(CreateForumRequest request);
    ReplyEntity replyToPost(CreateReplyRequest request, UUID postForumId);
    ForumEntity getAllReplies(UUID postForumId);
    LikeEntity likeAPost(UUID postForumId);
}
