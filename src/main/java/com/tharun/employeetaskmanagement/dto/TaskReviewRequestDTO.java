package com.tharun.employeetaskmanagement.dto;

import com.tharun.employeetaskmanagement.enums.ReviewDecision;
import jakarta.validation.constraints.NotNull;

/*
 * DTO used to receive Task Review data from API requests.
 * Task ID and reviewer ID are handled separately by the API.
 */
public class TaskReviewRequestDTO {

    private String comments;

    @NotNull(message = "Review decision is required")
    private ReviewDecision decision;

    /*
     * Returns the review comments.
     */
    public String getComments() {
        return comments;
    }

    /*
     * Sets the review comments.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /*
     * Returns the review decision.
     */
    public ReviewDecision getDecision() {
        return decision;
    }

    /*
     * Sets the review decision.
     */
    public void setDecision(ReviewDecision decision) {
        this.decision = decision;
    }
}