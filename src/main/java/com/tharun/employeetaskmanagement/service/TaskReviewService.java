package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.repository.TaskRepository;
import com.tharun.employeetaskmanagement.repository.TaskReviewRepository;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.tharun.employeetaskmanagement.entity.Task;
import com.tharun.employeetaskmanagement.entity.TaskReview;
import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.enums.ReviewDecision;
import com.tharun.employeetaskmanagement.enums.TaskStatus;
import com.tharun.employeetaskmanagement.exception.TaskNotFoundException;
import com.tharun.employeetaskmanagement.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Service layer for Task Review-related business logic.
 * It manages reviews and updates the related task.
 */
@Service
public class TaskReviewService {

    // Repository used to save and retrieve task reviews.
    private final TaskReviewRepository taskReviewRepository;

    // Repository used to find and update tasks.
    private final TaskRepository taskRepository;

    // Repository used to find the User who performs the review.
    private final UserRepository userRepository;

    /*
     * Constructor injection.
     * Spring provides all required repositories automatically.
     */
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
     * Only an Admin can review a task that is UNDER_REVIEW.
     */
    public TaskReview reviewTask(
            Long taskId,
            Long reviewerId,
            TaskReview review) {

        // Find the task.
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + taskId));

        // Find the user performing the review.
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + reviewerId));

        // Only Admin users can review tasks.
        if (!"ADMIN".equals(reviewer.getRole())) {
            throw new IllegalStateException(
                    "Only Admin users can review tasks");
        }

        // Only tasks in UNDER_REVIEW status can be reviewed.
        if (task.getStatus() != TaskStatus.UNDER_REVIEW) {
            throw new IllegalStateException(
                    "Only tasks in UNDER_REVIEW status can be reviewed");
        }

        // A review must contain a decision.
        if (review.getDecision() == null) {
            throw new IllegalStateException(
                    "Review decision is required");
        }

        // Connect the review to the task and reviewer.
        review.setTask(task);
        review.setReviewer(reviewer);
        review.setReviewedAt(LocalDateTime.now());

        // Update the task according to the review decision.
        if (review.getDecision() == ReviewDecision.APPROVED) {
            task.setStatus(TaskStatus.COMPLETED);
        } else {
            task.setStatus(TaskStatus.CHANGES_REQUESTED);
        }

        // Save the review and updated task.
        TaskReview savedReview = taskReviewRepository.save(review);
        taskRepository.save(task);

        return savedReview;
    }
    /*
     * Retrieves all reviews for a specific task.
     */
    public List<TaskReview> getReviewsByTask(Long taskId) {

        // Make sure the task exists first.
        taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + taskId));

        // Return all reviews belonging to the task.
        return taskReviewRepository.findByTaskId(taskId);
    }
}