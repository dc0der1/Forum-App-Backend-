package com.example.freeforall.services;

import com.example.freeforall.dtos.CreateForumRequest;
import com.example.freeforall.dtos.CreateReplyRequest;
import com.example.freeforall.dtos.UpdateForumRequest;
import com.example.freeforall.entities.ForumEntity;
import com.example.freeforall.entities.LikeEntity;
import com.example.freeforall.entities.ReplyEntity;
import com.example.freeforall.entities.UserEntity;
import com.example.freeforall.exceptions.InvalidOwnerShipException;
import com.example.freeforall.exceptions.InvalidReplyException;
import com.example.freeforall.exceptions.forum.InvalidCategoryException;
import com.example.freeforall.exceptions.forum.InvalidPostContentException;
import com.example.freeforall.exceptions.forum.InvalidPostTitleException;
import com.example.freeforall.exceptions.forum.PostNotFoundException;
import com.example.freeforall.exceptions.user.UserNotFoundException;
import com.example.freeforall.repositories.IForumRepository;
import com.example.freeforall.repositories.ILikeRepository;
import com.example.freeforall.repositories.IReplyRepository;
import com.example.freeforall.repositories.IUserRepository;
import com.example.freeforall.utils.AuthUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ForumService implements IForumService{

    private final IUserRepository userRepository;
    private final IForumRepository forumRepository;
    private final IReplyRepository replyRepository;
    private final ILikeRepository likeRepository;

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

    public Page<ForumEntity> getAllForumPosts(Pageable pageable) {

        if (forumRepository.findAll().isEmpty()) {
            throw new PostNotFoundException("There are no forum posts right now.");
        }

        return forumRepository.findAll(pageable);
    }

    @Override
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

    @Override
    public ForumEntity getAllReplies(UUID postForumId) {

        if (forumRepository.findById(postForumId).isEmpty()) {
            throw new PostNotFoundException("Couldn't find the post");
        }

        return forumRepository.findById(postForumId).get();
    }

    @Transactional
    @Override
    public LikeEntity likeAPost(UUID postForumId) {

        UserEntity userEntity = userRepository.findUserByEmail(AuthUtility.currentUser())
                .orElseThrow(() -> new UserNotFoundException("Could not find user!"));

        ForumEntity forumEntity = forumRepository.findById(postForumId)
                .orElseThrow(() -> new PostNotFoundException("Post could not be found!"));

        Optional<LikeEntity> existingLike = likeRepository.findByUserEntityAndForumEntity(userEntity, forumEntity);

        if (existingLike.isPresent()) {
            LikeEntity likeEntity = existingLike.get();

            if (likeEntity.isLiked()) {
                likeEntity.setLiked(false);
                forumEntity.setLikeCount(forumEntity.getLikeCount() - 1);
            } else {
                likeEntity.setLiked(true);
                forumEntity.setLikeCount(forumEntity.getLikeCount() + 1);
            }

            forumRepository.save(forumEntity);
            return likeRepository.save(likeEntity);
        } else {
            LikeEntity newLikeEntity = new LikeEntity(true, forumEntity, userEntity);

            forumEntity.setLikeCount(forumEntity.getLikeCount() + 1);

            forumRepository.save(forumEntity);
            return likeRepository.save(newLikeEntity);
        }
    }

    @Override
    public void deletePost(UUID postForumId) {

        UserEntity user = userRepository.findUserByEmail(AuthUtility.currentUser())
                .orElseThrow(() -> new UserNotFoundException("User could not be found!"));
        ForumEntity forumEntity = forumRepository.findById(postForumId)
                .orElseThrow(() -> new PostNotFoundException("Post could not be found!"));

        if (user.getId() != forumEntity.getUserEntity().getId()) {
            throw new InvalidOwnerShipException("This post isn't owned by " + user.getUsername());
        }

        forumRepository.delete(forumEntity);
    }

    public ForumEntity updatePost(UUID postForumId, UpdateForumRequest request) {

        UserEntity userEntity = userRepository.findUserByEmail(AuthUtility.currentUser())
                .orElseThrow(() -> new UserNotFoundException("User could not be found!"));
        ForumEntity forumEntity = forumRepository.findById(postForumId)
                .orElseThrow(() -> new PostNotFoundException("Post could not be found!"));

        if (userEntity.getId() != forumEntity.getUserEntity().getId()) {
            throw new InvalidOwnerShipException("This post isn't owned by " + userEntity.getUsername());
        }

        forumEntity.setPostTitle(request.getPostTitle());
        forumEntity.setPostContent(request.getPostContent());
        forumEntity.setCategory(request.getCategory());

        return forumRepository.save(forumEntity);
    }
}
