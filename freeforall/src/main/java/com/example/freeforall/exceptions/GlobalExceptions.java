package com.example.freeforall.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptions {
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleUserExists(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", "Already exists", "error", e.getMessage()));
    }

    @ExceptionHandler({InvalidPasswordException.class, InvalidEmailException.class,
            InvalidUsernameException.class, InvalidLoginException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(Exception e) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "Validation Error", "error", e.getMessage()));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleDTOValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Validation failed");
        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralError(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError()
                .body(Map.of("message", "An unexpected error occurred"));
    }
}
