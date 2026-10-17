package com.example.freeforall.exceptions.forum;

public class InvalidPostTitleException extends RuntimeException {
    public InvalidPostTitleException(String message) {
        super(message);
    }
}
