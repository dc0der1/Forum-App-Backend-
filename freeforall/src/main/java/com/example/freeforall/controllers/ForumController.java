package com.example.freeforall.controllers;

import com.example.freeforall.dtos.CreateForumRequest;
import com.example.freeforall.dtos.ForumResponse;
import com.example.freeforall.entities.ForumEntity;
import com.example.freeforall.services.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumService forumService;

    @PostMapping("/create")
    public ResponseEntity<?> createForumPost(@RequestBody CreateForumRequest request) {
        return ResponseEntity.created(URI.create("/api/forum/create"))
                .body(ForumResponse.fromModel(forumService.createForum(request)));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getForumPosts() {
        List<ForumEntity> forumEntities = forumService.getAllForumPosts();
        return ResponseEntity.ok().body(forumEntities.stream().map(ForumResponse::fromModel).toList());
    }
}
