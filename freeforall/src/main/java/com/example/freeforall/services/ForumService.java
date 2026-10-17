package com.example.freeforall.services;

import com.example.freeforall.dtos.CreateForumRequest;
import com.example.freeforall.dtos.CreateReplyRequest;
import com.example.freeforall.entities.ForumEntity;
import com.example.freeforall.entities.ReplyEntity;
import com.example.freeforall.entities.UserEntity;
import com.example.freeforall.exceptions.InvalidReplyException;
import com.example.freeforall.exceptions.forum.InvalidCategoryException;
import com.example.freeforall.exceptions.forum.InvalidPostContentException;
import com.example.freeforall.exceptions.forum.InvalidPostTitleException;
import com.example.freeforall.exceptions.forum.PostNotFoundException;
import com.example.freeforall.exceptions.user.UserNotFoundException;
import com.example.freeforall.repositories.IForumRepository;
import com.example.freeforall.repositories.IReplyRepository;
import com.example.freeforall.repositories.IUserRepository;
import com.example.freeforall.utils.AuthUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForumService implements IForumService{

    private final IUserRepository userRepository;
    private final IForumRepository forumRepository;
    private final IReplyRepository replyRepository;

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

    public ReplyEntity replyToPost(CreateReplyRequest request, UUID postForumId) {

        if (request.getReply().isEmpty() || request.getReply().isBlank()) {
            throw new InvalidReplyException("Reply cannot be empty!");
        }

        if (forumRepository.findById(postForumId).isEmpty()) {
            throw new PostNotFoundException("Post could not be found!");
        }

        ForumEntity forumEntity = forumRepository.findById(postForumId).get();
        forumEntity.setReplyCount(forumEntity.getReplies().size() + 1);

        if (userRepository.findUserByEmail(AuthUtility.currentUser()).isEmpty()) {
            throw new UserNotFoundException("Could not find user!");
        }

        UserEntity userEntity = userRepository.findUserByEmail(AuthUtility.currentUser()).get();

        ReplyEntity replyEntity = new ReplyEntity(request.getReply(), forumEntity, userEntity);
        replyRepository.save(replyEntity);

        return replyEntity;
    }
}
