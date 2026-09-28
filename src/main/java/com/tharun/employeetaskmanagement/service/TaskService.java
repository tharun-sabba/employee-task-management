package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.repository.EmployeeRepository;
import com.tharun.employeetaskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.tharun.employeetaskmanagement.entity.Employee;
import com.tharun.employeetaskmanagement.entity.Task;
import com.tharun.employeetaskmanagement.enums.TaskStatus;
import com.tharun.employeetaskmanagement.exception.EmployeeNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import com.tharun.employeetaskmanagement.exception.TaskNotFoundException;
import com.tharun.employeetaskmanagement.exception.InvalidTaskStatusException;
import com.tharun.employeetaskmanagement.exception.TaskSubmissionException;
import com.tharun.employeetaskmanagement.exception.InactiveEmployeeException;
/*
 * Service layer for Task-related business logic.
 * It uses TaskRepository to manage tasks and EmployeeRepository
 * to verify the employee assigned to a task.
 */
@Service
public class TaskService {

    // Repository used to access Task data in the database.
    private final TaskRepository taskRepository;

    // Repository used to find and verify employees.
    private final EmployeeRepository employeeRepository;

    /*
     * Constructor injection.
     * Spring provides both repository objects automatically.
     */
    public TaskService(
            TaskRepository taskRepository,
            EmployeeRepository employeeRepository) {

        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }
    /*
     * Creates a new task and assigns it to an existing active employee.
     */
    public Task createTask(Long employeeId, Task task) {

        // Find the employee who should receive the task.
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        /*
         * Inactive employees should not receive new tasks.
         */
        if (!"ACTIVE".equals(employee.getUser().getStatus())) {
            throw new InactiveEmployeeException(
                    "Cannot assign a task to an inactive employee");
        }

        // Link the task to the selected employee.
        task.setAssignedTo(employee);

        // Every newly created task starts in the ASSIGNED state.
        task.setStatus(TaskStatus.ASSIGNED);

        // Record when the task was created.
        task.setCreatedAt(LocalDateTime.now());

        // Save the task in the database.
        return taskRepository.save(task);
    }
    /*
     * Retrieves all tasks from the database.
     * The repository provides the findAll() method through JpaRepository.
     */
    public List<Task> getAllTasks() {

        // Fetch all task records from the database.
        return taskRepository.findAll();
    }
    /*
     * Retrieves a task by its ID.
     * Throws an exception if the task does not exist.
     */
    public Task getTaskById(Long id) {

        // Search the database for the task.
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id));
    }
    /*
     * Updates the status of an existing task.
     * Only valid status transitions are allowed.
     */
    public Task updateTaskStatus(Long taskId, TaskStatus newStatus) {

        // Find the existing task.
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + taskId));

        // Get the current status of the task.
        TaskStatus currentStatus = task.getStatus();

        // Check whether the requested status transition is valid.
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new InvalidTaskStatusException(
                    "Invalid status transition from "
                            + currentStatus + " to " + newStatus);
        }

        // Update the task status.
        task.setStatus(newStatus);

        // Save the updated task.
        return taskRepository.save(task);
    }

    /*
     * Checks whether a task can move from its current status
     * to the requested new status.
     */
    private boolean isValidStatusTransition(
            TaskStatus currentStatus,
            TaskStatus newStatus) {

        return switch (currentStatus) {
            case ASSIGNED -> newStatus == TaskStatus.IN_PROGRESS;
            case IN_PROGRESS -> newStatus == TaskStatus.SUBMITTED;
            case SUBMITTED -> newStatus == TaskStatus.UNDER_REVIEW;
            case UNDER_REVIEW ->
                    newStatus == TaskStatus.COMPLETED
                            || newStatus == TaskStatus.CHANGES_REQUESTED;
            case CHANGES_REQUESTED -> newStatus == TaskStatus.IN_PROGRESS;
            case COMPLETED -> false;
        };
    }
    /*
     * Retrieves all tasks assigned to a specific employee.
     */
    public List<Task> getTasksByEmployee(Long employeeId) {

        // Make sure the employee exists before searching for tasks.
        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        // Fetch all tasks assigned to the employee.
        return taskRepository.findByAssignedToId(employeeId);
    }
    /*
     * Submits a task for review.
     * The employee must be assigned to the task,
     * and the task must currently be IN_PROGRESS.
     */
    public Task submitTask(Long taskId, Long employeeId) {

        // Find the task.
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + taskId));

        // Verify that the task belongs to the employee submitting it.
        if (!task.getAssignedTo().getId().equals(employeeId)) {
            throw new TaskSubmissionException(
                    "Employee is not assigned to this task");
        }

        // A task can only be submitted when it is IN_PROGRESS.
        if (task.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new TaskSubmissionException(
                    "Only tasks in IN_PROGRESS status can be submitted");
        }

        // Move the task to SUBMITTED status.
        task.setStatus(TaskStatus.SUBMITTED);

        // Save the updated task.
        return taskRepository.save(task);
    }
}