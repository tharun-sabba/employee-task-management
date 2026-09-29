package com.tharun.employeetaskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import com.tharun.employeetaskmanagement.exception.TaskSubmissionException;
import com.tharun.employeetaskmanagement.exception.InactiveEmployeeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.LinkedHashMap;

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
    /*
     * Handles EmployeeNotFoundException and returns HTTP 404.
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFound(
            EmployeeNotFoundException exception) {

        // Create a simple error response for the API client.
        Map<String, String> error = Map.of(
                "message", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    /*
     * Handles TaskNotFoundException and returns HTTP 404.
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFound(
            TaskNotFoundException exception) {

        // Create a simple error response for the API client.
        Map<String, String> error = Map.of(
                "message", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    /*
     * Handles invalid task status transitions.
     * Returns HTTP 400 Bad Request because the requested
     * status change is not allowed by the business rules.
     */
    @ExceptionHandler(InvalidTaskStatusException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTaskStatus(
            InvalidTaskStatusException exception) {

        // Create a simple error response for the API client.
        Map<String, String> error = Map.of(
                "message", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    /*
     * Handles task submission business-rule errors.
     * Returns HTTP 400 Bad Request.
     */
    @ExceptionHandler(TaskSubmissionException.class)
    public ResponseEntity<Map<String, String>> handleTaskSubmission(
            TaskSubmissionException exception) {

        // Create a simple error response for the API client.
        Map<String, String> error = Map.of(
                "message", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    /*
     * Handles attempts to assign tasks to inactive employees.
     * Returns HTTP 400 Bad Request.
     */
    @ExceptionHandler(InactiveEmployeeException.class)
    public ResponseEntity<Map<String, String>> handleInactiveEmployee(
            InactiveEmployeeException exception) {

        // Create a simple error response for the API client.
        Map<String, String> error = Map.of(
                "message", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    /*
     * Handles validation errors from @Valid request bodies.
     * Returns HTTP 400 with the invalid fields and their messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        // Store validation errors as field → message.
        Map<String, String> errors = new LinkedHashMap<>();

        // Collect each field's validation error.
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        // Return the validation errors with HTTP 400.
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}