package com.example.freeforall.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ForumEntity {

    @Id
    private final UUID id = UUID.randomUUID();

    private String postTitle;
    private String postContent;
    private String category;

    @Column(name = "created_at", updatable = false)
    private final Date createdAt = new Date();
    private int likeCount;
    private int replyCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    public ForumEntity(String postTitle, String postContent, int likeCount, int replyCount, String category, UserEntity userEntity) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.category = category;
        this.userEntity = userEntity;
    }
}
