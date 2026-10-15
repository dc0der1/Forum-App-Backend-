package com.example.freeforall.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateForumRequest {
    private String postTitle;
    private String postContent;
    private String category;
}
