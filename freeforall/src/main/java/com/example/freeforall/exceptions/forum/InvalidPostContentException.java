package com.example.freeforall.exceptions.forum;

public class InvalidPostContentException extends RuntimeException {
    public InvalidPostContentException(String message) {
        super(message);
    }
}
