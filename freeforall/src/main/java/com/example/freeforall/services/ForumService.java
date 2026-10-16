package com.example.freeforall.services;

import com.example.freeforall.dtos.CreateForumRequest;
import com.example.freeforall.entities.ForumEntity;
import com.example.freeforall.entities.UserEntity;
import com.example.freeforall.exceptions.InvalidCategoryException;
import com.example.freeforall.exceptions.InvalidPostContentException;
import com.example.freeforall.exceptions.InvalidPostTitleException;
import com.example.freeforall.exceptions.PostNotFoundException;
import com.example.freeforall.exceptions.user.UserNotFoundException;
import com.example.freeforall.repositories.IForumRepository;
import com.example.freeforall.repositories.IUserRepository;
import com.example.freeforall.utils.AuthUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumService implements IForumService{

    private final IUserRepository userRepository;
    private final IForumRepository forumRepository;

    @Override
    public ForumEntity createForum(CreateForumRequest request) {

        if (request.getPostTitle().isEmpty() || request.getPostTitle().isBlank()) {
            throw new InvalidPostTitleException("Post title cannot be empty!");
        }

        if (request.getPostContent().isEmpty() || request.getPostContent().isBlank()) {
            throw new InvalidPostContentException("Post content cannot be empty!");
        }

        if (request.getCategory().isEmpty() || request.getCategory().isBlank()) {
            throw new InvalidCategoryException("Post category cannot be empty!");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findUserByEmail(AuthUtility.currentUser());

        if (userEntityOptional.isEmpty()) {
            throw new UserNotFoundException("User could not be found!");
        }

        UserEntity user = userEntityOptional.get();

        ForumEntity forum = new ForumEntity(request.getPostTitle(), request.getPostContent(), 0, 0, request.getCategory(), user);
        forumRepository.save(forum);

        return forum;
    }

    public List<ForumEntity> getAllForumPosts() {

        if (forumRepository.findAll().isEmpty()) {
            throw new PostNotFoundException("There are no forum posts right now.");
        }

        return forumRepository.findAll();
    }
}
