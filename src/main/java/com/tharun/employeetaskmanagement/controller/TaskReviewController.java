package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.dto.TaskReviewRequestDTO;
import com.tharun.employeetaskmanagement.dto.TaskReviewResponseDTO;
import com.tharun.employeetaskmanagement.service.TaskReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller for handling Task Review API requests.
 * It receives HTTP requests and passes the work to TaskReviewService.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskReviewController {

    private final TaskReviewService taskReviewService;

    public TaskReviewController(TaskReviewService taskReviewService) {
        this.taskReviewService = taskReviewService;
    }

    /*
     * Creates a review for a submitted task.
     * The reviewer ID is received as a request parameter.
     */
    @PostMapping("/{taskId}/reviews")
    public TaskReviewResponseDTO reviewTask(
            @PathVariable Long taskId,
            @RequestParam Long reviewerId,
            @Valid @RequestBody TaskReviewRequestDTO requestDTO) {

        return taskReviewService.reviewTask(
                taskId,
                reviewerId,
                requestDTO
        );
    }

    /*
     * Retrieves all reviews for a particular task.
     */
    @GetMapping("/{taskId}/reviews")
    public List<TaskReviewResponseDTO> getReviewsByTask(
            @PathVariable Long taskId) {

        return taskReviewService.getReviewsByTask(taskId);
    }
}