package com.tharun.employeetaskmanagement.exception;

/*
 * Exception thrown when a task is assigned
 * to an employee whose account is inactive.
 */
public class InactiveEmployeeException extends RuntimeException {

    // Creates the exception with a useful error message.
    public InactiveEmployeeException(String message) {
        super(message);
    }
}