package com.example.freeforall.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ForumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    private String postTitle;
    private String postContent;
    private int likes;
    private int replies;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
