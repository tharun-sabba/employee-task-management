package com.tharun.employeetaskmanagement.repository;

import com.tharun.employeetaskmanagement.entity.TaskReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Repository used to access TaskReview data in the database.
 * JpaRepository provides common CRUD operations automatically.
 */
public interface TaskReviewRepository extends JpaRepository<TaskReview, Long> {

    /*
     * Finds all reviews associated with a specific task.
     */
    List<TaskReview> findByTaskId(Long taskId);
}