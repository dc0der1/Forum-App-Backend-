package com.example.freeforall.controllers;

import com.example.freeforall.dtos.CreateForumRequest;
import com.example.freeforall.dtos.ForumResponse;
import com.example.freeforall.services.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumService forumService;

    @PostMapping("/create")
    public ResponseEntity<?> createForumPost(@RequestBody CreateForumRequest request) {
        return ResponseEntity.ok().body(ForumResponse.fromModel(forumService.createForum(request)));
    }
}
