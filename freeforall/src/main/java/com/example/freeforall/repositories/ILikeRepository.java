package com.example.freeforall.repositories;

import com.example.freeforall.entities.ForumEntity;
import com.example.freeforall.entities.LikeEntity;
import com.example.freeforall.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ILikeRepository extends JpaRepository<LikeEntity, UUID> {
    Optional<LikeEntity> findByUserEntityAndForumEntity(UserEntity userEntity, ForumEntity forumEntity);
}
