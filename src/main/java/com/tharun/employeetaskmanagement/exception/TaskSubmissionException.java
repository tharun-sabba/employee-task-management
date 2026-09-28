package com.tharun.employeetaskmanagement.exception;

/*
 * Exception thrown when a task submission violates
 * one of the task submission business rules.
 */
public class TaskSubmissionException extends RuntimeException {

    // Creates the exception with a useful error message.
    public TaskSubmissionException(String message) {
        super(message);
    }
}