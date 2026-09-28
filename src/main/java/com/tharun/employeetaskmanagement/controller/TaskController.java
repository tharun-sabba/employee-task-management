package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.entity.Task;
import com.tharun.employeetaskmanagement.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.tharun.employeetaskmanagement.enums.TaskStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
/*
 * Controller for handling Task-related API requests.
 * It receives HTTP requests and passes the work to TaskService.
 */
@RestController
public class TaskController {

    private final TaskService taskService;

    /*
     * Constructor injection.
     * Spring provides the TaskService object automatically.
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /*
     * Creates a task and assigns it to an existing employee.
     * The employee ID is taken from the URL.
     */
    @PostMapping("/api/tasks/{employeeId}")
    public Task createTask(
            @PathVariable Long employeeId,
            @RequestBody Task task) {

        // Send the employee ID and task details to the Service layer.
        return taskService.createTask(employeeId, task);
    }
    /*
     * Retrieves all tasks from the database.
     * Calls the Service layer to fetch the task records.
     */
    @GetMapping("/api/tasks")
    public List<Task> getAllTasks() {

        // Ask the Service layer for all tasks.
        return taskService.getAllTasks();
    }
    /*
     * Retrieves a single task using the task ID from the URL.
     */
    @GetMapping("/api/tasks/{id}")
    public Task getTaskById(@PathVariable Long id) {

        // Send the task ID to the Service layer.
        return taskService.getTaskById(id);
    }
    /*
     * Updates the status of a task.
     * The task ID comes from the URL and the new status comes
     * from the request parameter.
     */
    @PatchMapping("/api/tasks/{id}/status")
    public Task updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {

        // Send the task ID and new status to the Service layer.
        return taskService.updateTaskStatus(id, status);
    }
    /*
     * Retrieves all tasks assigned to a specific employee.
     */
    @GetMapping("/api/tasks/employee/{employeeId}")
    public List<Task> getTasksByEmployee(@PathVariable Long employeeId) {

        // Ask the Service layer for this employee's tasks.
        return taskService.getTasksByEmployee(employeeId);
    }
    /*
     * Submits a task for review.
     * The task ID and employee ID come from the URL.
     */
    @PostMapping("/api/tasks/{taskId}/submit")
    public Task submitTask(
            @PathVariable Long taskId,
            @RequestParam Long employeeId) {

        // Send the task ID and employee ID to the Service layer.
        return taskService.submitTask(taskId, employeeId);
    }
}