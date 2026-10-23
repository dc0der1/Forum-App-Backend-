package com.example.freeforall.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateForumRequest {
    private String postTitle;
    private String postContent;
    private String category;
}
