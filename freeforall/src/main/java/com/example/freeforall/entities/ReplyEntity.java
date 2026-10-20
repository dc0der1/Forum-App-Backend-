package com.example.freeforall.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity(name = "replies")
@Getter
@Setter
@RequiredArgsConstructor
public class ReplyEntity {

    @Id
    @Column(name = "reply_id")
    private final UUID id = UUID.randomUUID();

    private String reply;

    @Column(name = "created_at", updatable = false)
    private final Date createdAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id")
    private ForumEntity forumEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public ReplyEntity(String reply, ForumEntity forumEntity, UserEntity userEntity) {
        this.reply = reply;
        this.forumEntity = forumEntity;
        this.userEntity = userEntity;
    }
}
