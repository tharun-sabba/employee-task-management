package com.tharun.employeetaskmanagement.entity;

import com.tharun.employeetaskmanagement.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * Represents a task in the system.
 * A task can be assigned to an Employee.
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Short title describing the task.
    private String title;

    // Detailed description of the task.
    private String description;

    /*
     * Stores the current task status.
     * EnumType.STRING stores values such as ASSIGNED in MySQL.
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    /*
     * Many tasks can belong to one employee.
     * assigned_to stores the Employee's ID as a foreign key.
     */
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Employee assignedTo;

    // Date by which the task should be completed.
    private LocalDate dueDate;

    // Date and time when the task was created.
    private LocalDateTime createdAt;

    // Returns the task ID.
    public Long getId() {
        return id;
    }

    // Sets the task ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Returns the task title.
    public String getTitle() {
        return title;
    }

    // Sets the task title.
    public void setTitle(String title) {
        this.title = title;
    }

    // Returns the task description.
    public String getDescription() {
        return description;
    }

    // Sets the task description.
    public void setDescription(String description) {
        this.description = description;
    }

    // Returns the current task status.
    public TaskStatus getStatus() {
        return status;
    }

    // Sets the task status.
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // Returns the employee assigned to this task.
    public Employee getAssignedTo() {
        return assignedTo;
    }

    // Sets the employee assigned to this task.
    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    // Returns the task due date.
    public LocalDate getDueDate() {
        return dueDate;
    }

    // Sets the task due date.
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // Returns the task creation time.
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Sets the task creation time.
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}