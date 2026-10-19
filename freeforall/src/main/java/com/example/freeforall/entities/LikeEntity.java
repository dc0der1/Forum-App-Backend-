package com.example.freeforall.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity(name = "likes")
@Getter
@Setter
@RequiredArgsConstructor
public class LikeEntity {

    @Id
    @Column(name = "like_id")
    private final UUID id = UUID.randomUUID();

    private boolean isLiked;

    @Column(name = "liked_at", updatable = false)
    private final Date likedAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id")
    private ForumEntity forumEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public LikeEntity(boolean isLiked, ForumEntity forumEntity, UserEntity userEntity) {
        this.isLiked = isLiked;
        this.forumEntity = forumEntity;
        this.userEntity = userEntity;
    }
}
