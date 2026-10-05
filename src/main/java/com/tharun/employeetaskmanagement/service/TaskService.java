package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.dto.TaskRequestDTO;
import com.tharun.employeetaskmanagement.dto.TaskResponseDTO;
import com.tharun.employeetaskmanagement.entity.Employee;
import com.tharun.employeetaskmanagement.entity.Task;
import com.tharun.employeetaskmanagement.enums.TaskStatus;
import com.tharun.employeetaskmanagement.exception.EmployeeNotFoundException;
import com.tharun.employeetaskmanagement.exception.InactiveEmployeeException;
import com.tharun.employeetaskmanagement.exception.InvalidTaskStatusException;
import com.tharun.employeetaskmanagement.exception.TaskNotFoundException;
import com.tharun.employeetaskmanagement.exception.TaskSubmissionException;
import com.tharun.employeetaskmanagement.repository.EmployeeRepository;
import com.tharun.employeetaskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.tharun.employeetaskmanagement.exception.EmployeeNotFoundException;

/*
 * Service layer for Task-related business logic.
 * It handles task creation, status changes, and submission.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(
            TaskRepository taskRepository,
            EmployeeRepository employeeRepository) {

        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    /*
     * Creates a new Task and assigns it to an Employee.
     * The server controls the initial status and creation time.
     */
    public TaskResponseDTO createTask(
            Long employeeId,
            TaskRequestDTO requestDTO) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        if (employee.getUser() != null
                && "INACTIVE".equalsIgnoreCase(employee.getUser().getStatus())) {

            throw new InactiveEmployeeException(
                    "Cannot assign task to an inactive employee");
        }

        Task task = new Task();

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setDueDate(requestDTO.getDueDate());

        /*
         * New tasks always start with ASSIGNED status.
         */
        task.setStatus(TaskStatus.ASSIGNED);

        task.setAssignedTo(employee);
        task.setCreatedAt(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);

        return convertToResponseDTO(savedTask);
    }

    /*
     * Retrieves all tasks.
     */
    public List<TaskResponseDTO> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    /*
     * Retrieves one task by ID.
     */
    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id));

        return convertToResponseDTO(task);
    }

    /*
     * Updates the status of a task.
     * Only valid workflow transitions are allowed.
     */
    public TaskResponseDTO updateTaskStatus(
            Long id,
            TaskStatus newStatus) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id));

        TaskStatus currentStatus = task.getStatus();

        boolean validTransition = switch (currentStatus) {

            case ASSIGNED ->
                    newStatus == TaskStatus.IN_PROGRESS;

            case IN_PROGRESS ->
                    newStatus == TaskStatus.SUBMITTED;

            case SUBMITTED ->
                    newStatus == TaskStatus.UNDER_REVIEW;

            case UNDER_REVIEW ->
                    newStatus == TaskStatus.COMPLETED
                            || newStatus == TaskStatus.CHANGES_REQUESTED;

            case CHANGES_REQUESTED ->
                    newStatus == TaskStatus.IN_PROGRESS;

            case COMPLETED ->
                    false;
        };

        if (!validTransition) {
            throw new InvalidTaskStatusException(
                    "Invalid task status transition from "
                            + currentStatus
                            + " to "
                            + newStatus);
        }

        task.setStatus(newStatus);

        Task savedTask = taskRepository.save(task);

        return convertToResponseDTO(savedTask);
    }

    /*
     * Retrieves all tasks assigned to a particular employee.
     */
    public List<TaskResponseDTO> getTasksByEmployee(
            Long employeeId) {

        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        return taskRepository.findByAssignedToId(employeeId)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    /*
     * Submits a task by the employee to whom it is assigned.
     */
    public TaskResponseDTO submitTask(
            Long taskId,
            Long employeeId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + taskId));

        if (task.getAssignedTo() == null
                || !task.getAssignedTo().getId().equals(employeeId)) {

            throw new TaskSubmissionException(
                    "Employee is not assigned to this task");
        }

        if (task.getStatus() != TaskStatus.IN_PROGRESS) {

            throw new TaskSubmissionException(
                    "Task can only be submitted when it is IN_PROGRESS");
        }

        task.setStatus(TaskStatus.SUBMITTED);

        Task savedTask = taskRepository.save(task);

        return convertToResponseDTO(savedTask);
    }

    /*
     * Converts a Task entity into a safe response DTO.
     * Only selected Employee and User information is included.
     */
    private TaskResponseDTO convertToResponseDTO(Task task) {

        TaskResponseDTO responseDTO = new TaskResponseDTO();

        responseDTO.setId(task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setStatus(task.getStatus());
        responseDTO.setDueDate(task.getDueDate());
        responseDTO.setCreatedAt(task.getCreatedAt());

        if (task.getAssignedTo() != null) {

            Employee employee = task.getAssignedTo();

            responseDTO.setEmployeeId(employee.getId());
            responseDTO.setEmployeeName(employee.getName());
            responseDTO.setEmployeeDepartment(
                    employee.getDepartment());

            if (employee.getUser() != null) {
                responseDTO.setEmployeeEmail(
                        employee.getUser().getEmail());
            }
        }

        return responseDTO;
    }
    /*
     * Retrieves tasks belonging to the currently logged-in employee.
     * The User ID comes from the JWT.
     */
    public List<TaskResponseDTO> getMyTasks(Long userId) {

        /*
         * Find the employee linked to the logged-in User.
         */
        Employee employee = employeeRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found for user id: " + userId));

        /*
         * Fetch only tasks assigned to this employee.
         */
        return taskRepository.findByAssignedToId(employee.getId())
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }
}