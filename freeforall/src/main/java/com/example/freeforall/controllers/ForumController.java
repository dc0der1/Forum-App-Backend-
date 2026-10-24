package com.example.freeforall.controllers;

import com.example.freeforall.dtos.*;
import com.example.freeforall.entities.ForumEntity;
import com.example.freeforall.services.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<?> getForumPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postTitle") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok().body(forumService.getAllForumPosts(PageRequest.of(page, size, sort)).stream().map(ForumResponse::fromModel).toList());
    }

    @PostMapping("/reply")
    public ResponseEntity<?> replyToPost(@RequestBody CreateReplyRequest request, @RequestParam UUID forumPostId) {
        return ResponseEntity.created(URI.create("/api/forum/reply"))
                .body(ReplyResponse.fromModel(forumService.replyToPost(request, forumPostId)));
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeAPost(@RequestParam UUID forumPostId) {
        return ResponseEntity.created(URI.create("/api/forum/like"))
                .body(LikeResponse.fromModel(forumService.likeAPost(forumPostId)));
    }

    @GetMapping("/replies")
    public ResponseEntity<?> getAllPostReplies(@RequestParam UUID forumPostId) {
        return ResponseEntity.ok().body(ForumResponse.fromModel(forumService.getAllReplies(forumPostId)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAPost(@RequestParam UUID forumPostId) {
        forumService.deletePost(forumPostId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updatePost(@RequestParam UUID forumPostId, @RequestBody UpdateForumRequest request) {
        return ResponseEntity.ok().body(ForumResponse.fromModel(forumService.updatePost(forumPostId, request)));
    }

    @GetMapping("/category")
    public ResponseEntity<?> findPostsByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "forum.post_title") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam String category
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok().body(forumService.getPostByCategory(PageRequest.of(page, size, sort), category)
                .stream()
                .map(ForumResponse::fromModel)
                .toList());
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "forum.post_title") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok().body(forumService.getMostPopularPosts(PageRequest.of(page, size, sort))
                .stream()
                .map(ForumResponse::fromModel)
                .toList());
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "forum.post_title") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok().body(forumService.getLatestPosts(PageRequest.of(page, size, sort))
                .stream()
                .map(ForumResponse::fromModel)
                .toList());
    }
}
