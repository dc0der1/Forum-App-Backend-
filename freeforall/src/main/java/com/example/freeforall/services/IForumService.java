package com.example.freeforall.services;

import com.example.freeforall.dtos.CreateForumRequest;
import com.example.freeforall.entities.ForumEntity;

public interface IForumService {
    ForumEntity createForum(CreateForumRequest request);
}
