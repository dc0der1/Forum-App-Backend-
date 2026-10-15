package com.example.freeforall.exceptions;

public class InvalidPostTitleException extends RuntimeException {
    public InvalidPostTitleException(String message) {
        super(message);
    }
}
