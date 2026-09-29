package com.tharun.employeetaskmanagement.dto;

import com.tharun.employeetaskmanagement.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * DTO used to send Task data back to the client.
 * Only selected Employee information is included.
 */
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;

    private LocalDate dueDate;
    private LocalDateTime createdAt;

    private Long employeeId;
    private String employeeName;
    private String employeeDepartment;
    private String employeeEmail;

    /*
     * Returns the task ID.
     */
    public Long getId() {
        return id;
    }

    /*
     * Sets the task ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * Returns the current task status.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /*
     * Sets the current task status.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
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

    /*
     * Returns the task creation time.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /*
     * Sets the task creation time.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /*
     * Returns the assigned employee ID.
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /*
     * Sets the assigned employee ID.
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /*
     * Returns the assigned employee name.
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /*
     * Sets the assigned employee name.
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /*
     * Returns the assigned employee department.
     */
    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    /*
     * Sets the assigned employee department.
     */
    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    /*
     * Returns the assigned employee email.
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /*
     * Sets the assigned employee email.
     */
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}