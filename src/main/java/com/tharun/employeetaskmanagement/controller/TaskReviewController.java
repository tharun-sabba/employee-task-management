package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.entity.TaskReview;
import com.tharun.employeetaskmanagement.service.TaskReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/*
 * Controller for handling Task Review API requests.
 */
@RestController
public class TaskReviewController {

    private final TaskReviewService taskReviewService;

    /*
     * Constructor injection.
     */
    public TaskReviewController(TaskReviewService taskReviewService) {
        this.taskReviewService = taskReviewService;
    }

    /*
     * Reviews a task using the reviewer ID from the request parameter.
     */
    @PostMapping("/api/tasks/{taskId}/reviews")
    public TaskReview reviewTask(
            @PathVariable Long taskId,
            @RequestParam Long reviewerId,
            @RequestBody TaskReview review) {

        // Send the review information to the Service layer.
        return taskReviewService.reviewTask(
                taskId,
                reviewerId,
                review);
    }
    /*
     * Retrieves all reviews associated with a task.
     */
    @GetMapping("/api/tasks/{taskId}/reviews")
    public List<TaskReview> getReviewsByTask(
            @PathVariable Long taskId) {

        // Ask the Service layer for the task's review history.
        return taskReviewService.getReviewsByTask(taskId);
    }
}