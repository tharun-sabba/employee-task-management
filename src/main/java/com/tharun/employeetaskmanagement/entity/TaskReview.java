package com.tharun.employeetaskmanagement.entity;

import com.tharun.employeetaskmanagement.enums.ReviewDecision;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/*
 * Represents a review performed on a task.
 * A review belongs to one Task and is performed by one User.
 */
@Entity
@Table(name = "task_reviews")
public class TaskReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * The task being reviewed.
     * Many reviews can belong to one task.
     */
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    /*
     * The User who performed the review.
     */
    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    // Comments written by the reviewer.
    private String comments;

    /*
     * Stores the review decision as text such as
     * APPROVED or CHANGES_REQUESTED.
     */
    @Enumerated(EnumType.STRING)
    private ReviewDecision decision;

    // Date and time when the review was created.
    private LocalDateTime reviewedAt;

    // Returns the review ID.
    public Long getId() {
        return id;
    }

    // Sets the review ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Returns the task being reviewed.
    public Task getTask() {
        return task;
    }

    // Sets the task being reviewed.
    public void setTask(Task task) {
        this.task = task;
    }

    // Returns the reviewer.
    public User getReviewer() {
        return reviewer;
    }

    // Sets the reviewer.
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    // Returns the review comments.
    public String getComments() {
        return comments;
    }

    // Sets the review comments.
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Returns the review decision.
    public ReviewDecision getDecision() {
        return decision;
    }

    // Sets the review decision.
    public void setDecision(ReviewDecision decision) {
        this.decision = decision;
    }

    // Returns the review creation time.
    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    // Sets the review creation time.
    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}