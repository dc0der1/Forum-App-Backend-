package com.example.freeforall.exceptions;

public class InvalidPostContentException extends RuntimeException {
    public InvalidPostContentException(String message) {
        super(message);
    }
}
