package com.tharun.employeetaskmanagement.repository;

import com.tharun.employeetaskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Repository used to access Task data in the database.
 * JpaRepository provides common CRUD operations automatically.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /*
     * Finds all tasks assigned to a specific employee.
     */
    List<Task> findByAssignedToId(Long employeeId);
}