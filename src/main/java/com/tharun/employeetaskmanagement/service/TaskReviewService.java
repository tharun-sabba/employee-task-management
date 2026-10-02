package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.dto.TaskReviewRequestDTO;
import com.tharun.employeetaskmanagement.dto.TaskReviewResponseDTO;
import com.tharun.employeetaskmanagement.entity.Task;
import com.tharun.employeetaskmanagement.entity.TaskReview;
import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.enums.ReviewDecision;
import com.tharun.employeetaskmanagement.enums.TaskStatus;
import com.tharun.employeetaskmanagement.exception.TaskNotFoundException;
import com.tharun.employeetaskmanagement.exception.UserNotFoundException;
import com.tharun.employeetaskmanagement.repository.TaskRepository;
import com.tharun.employeetaskmanagement.repository.TaskReviewRepository;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Service layer for Task Review-related business logic.
 * It handles review creation and retrieval.
 */
@Service
public class TaskReviewService {

    private final TaskReviewRepository taskReviewRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskReviewService(
            TaskReviewRepository taskReviewRepository,
            TaskRepository taskRepository,
            UserRepository userRepository) {

        this.taskReviewRepository = taskReviewRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /*
     * Reviews a submitted task.
     * Only an Admin can approve or request changes.
     */
    public TaskReviewResponseDTO reviewTask(
            Long taskId,
            Long reviewerId,
            TaskReviewRequestDTO requestDTO) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + taskId));

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + reviewerId));

        /*
         * Only Admin users are allowed to review tasks.
         */
        if (!"ADMIN".equalsIgnoreCase(reviewer.getRole())) {
            throw new IllegalArgumentException(
                    "Only Admin users can review tasks");
        }

        /*
         * A task can only be reviewed after submission.
         */
        if (task.getStatus() != TaskStatus.UNDER_REVIEW) {
            throw new IllegalArgumentException(
                    "Task can only be reviewed when it is UNDER_REVIEW");
        }

        TaskReview review = new TaskReview();

        review.setTask(task);
        review.setReviewer(reviewer);
        review.setComments(requestDTO.getComments());
        review.setDecision(requestDTO.getDecision());
        review.setReviewedAt(LocalDateTime.now());

        /*
         * Update the task status according to the review decision.
         */
        if (requestDTO.getDecision() == ReviewDecision.APPROVED) {

            task.setStatus(TaskStatus.COMPLETED);

        } else if (requestDTO.getDecision()
                == ReviewDecision.CHANGES_REQUESTED) {

            task.setStatus(TaskStatus.CHANGES_REQUESTED);
        }

        TaskReview savedReview = taskReviewRepository.save(review);

        taskRepository.save(task);

        return convertToResponseDTO(savedReview);
    }

    /*
     * Retrieves all reviews for a particular task.
     */
    public List<TaskReviewResponseDTO> getReviewsByTask(
            Long taskId) {

        taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + taskId));

        return taskReviewRepository.findByTaskId(taskId)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    /*
     * Converts a TaskReview entity into a safe response DTO.
     */
    private TaskReviewResponseDTO convertToResponseDTO(
            TaskReview review) {

        TaskReviewResponseDTO responseDTO =
                new TaskReviewResponseDTO();

        responseDTO.setId(review.getId());
        responseDTO.setComments(review.getComments());
        responseDTO.setDecision(review.getDecision());
        responseDTO.setReviewedAt(review.getReviewedAt());

        if (review.getTask() != null) {

            responseDTO.setTaskId(review.getTask().getId());
            responseDTO.setTaskTitle(review.getTask().getTitle());
        }

        if (review.getReviewer() != null) {

            responseDTO.setReviewerId(
                    review.getReviewer().getId());

            responseDTO.setReviewerEmail(
                    review.getReviewer().getEmail());
        }

        return responseDTO;
    }
}