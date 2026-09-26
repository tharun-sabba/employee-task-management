package com.tharun.employeetaskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/*
 Handles exceptions thrown by REST controllers.
 This keeps error responses clean and consistent.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     Handles UserNotFoundException and returns HTTP 404.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(
            UserNotFoundException exception) {

        // Create a simple error response for the API client.
        Map<String, String> error = Map.of(
                "message", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}