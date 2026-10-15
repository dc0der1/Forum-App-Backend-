package com.example.freeforall.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private final UUID id = UUID.randomUUID();

    private String reply;

    @Column(name = "created_at", updatable = false)
    private final Date createdAt = new Date();

}
