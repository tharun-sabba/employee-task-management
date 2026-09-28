package com.tharun.employeetaskmanagement.exception;

/*
 * Exception thrown when a task tries to move
 * from one status to an invalid status.
 */
public class InvalidTaskStatusException extends RuntimeException {

    // Creates the exception with a useful error message.
    public InvalidTaskStatusException(String message) {
        super(message);
    }
}