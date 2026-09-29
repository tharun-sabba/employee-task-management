package com.tharun.employeetaskmanagement.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/*
 * DTO used to receive Task data from API requests.
 * The server controls task status, assignment, ID, and creation time.
 */
public class TaskRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

    /*
     * Returns the task title.
     */
    public String getTitle() {
        return title;
    }

    /*
     * Sets the task title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * Returns the task description.
     */
    public String getDescription() {
        return description;
    }

    /*
     * Sets the task description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * Returns the task due date.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /*
     * Sets the task due date.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}