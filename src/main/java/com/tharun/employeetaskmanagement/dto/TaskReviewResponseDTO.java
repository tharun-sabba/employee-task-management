package com.tharun.employeetaskmanagement.dto;

import com.tharun.employeetaskmanagement.enums.ReviewDecision;

import java.time.LocalDateTime;

/*
 * DTO used to send Task Review data back to the client.
 * Only selected Task and User information is included.
 */
public class TaskReviewResponseDTO {

    private Long id;

    private Long taskId;
    private String taskTitle;

    private Long reviewerId;
    private String reviewerEmail;

    private String comments;
    private ReviewDecision decision;
    private LocalDateTime reviewedAt;

    /*
     * Returns the review ID.
     */
    public Long getId() {
        return id;
    }

    /*
     * Sets the review ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /*
     * Returns the task ID.
     */
    public Long getTaskId() {
        return taskId;
    }

    /*
     * Sets the task ID.
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /*
     * Returns the task title.
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /*
     * Sets the task title.
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /*
     * Returns the reviewer ID.
     */
    public Long getReviewerId() {
        return reviewerId;
    }

    /*
     * Sets the reviewer ID.
     */
    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    /*
     * Returns the reviewer email.
     */
    public String getReviewerEmail() {
        return reviewerEmail;
    }

    /*
     * Sets the reviewer email.
     */
    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

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

    /*
     * Returns the review timestamp.
     */
    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    /*
     * Sets the review timestamp.
     */
    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}