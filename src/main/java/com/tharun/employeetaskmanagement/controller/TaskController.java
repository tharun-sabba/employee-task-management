package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.dto.TaskRequestDTO;
import com.tharun.employeetaskmanagement.dto.TaskResponseDTO;
import com.tharun.employeetaskmanagement.enums.TaskStatus;
import com.tharun.employeetaskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller for handling Task-related API requests.
 * It receives requests and passes the work to TaskService.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /*
     * Creates a new task and assigns it to an employee.
     */
    @PostMapping("/{employeeId}")
    public TaskResponseDTO createTask(
            @PathVariable Long employeeId,
            @Valid @RequestBody TaskRequestDTO requestDTO) {

        return taskService.createTask(employeeId, requestDTO);
    }

    /*
     * Retrieves all tasks.
     */
    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {

        return taskService.getAllTasks();
    }

    /*
     * Retrieves one task by ID.
     */
    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(
            @PathVariable Long id) {

        return taskService.getTaskById(id);
    }

    /*
     * Retrieves all tasks assigned to an employee.
     */
    @GetMapping("/employee/{employeeId}")
    public List<TaskResponseDTO> getTasksByEmployee(
            @PathVariable Long employeeId) {

        return taskService.getTasksByEmployee(employeeId);
    }

    /*
     * Updates the status of a task.
     */
    @PatchMapping("/{id}/status")
    public TaskResponseDTO updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {

        return taskService.updateTaskStatus(id, status);
    }

    /*
     * Submits a task by the assigned employee.
     */
    @PostMapping("/{taskId}/submit")
    public TaskResponseDTO submitTask(
            @PathVariable Long taskId,
            @RequestParam Long employeeId) {

        return taskService.submitTask(taskId, employeeId);
    }
}