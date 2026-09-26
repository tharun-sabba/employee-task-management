package com.tharun.employeetaskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Exception thrown when a requested user does not exist.
 * Spring automatically returns HTTP 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    // Creates the exception with a useful error message.
    public UserNotFoundException(String message) {
        super(message);
    }
}