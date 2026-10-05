package com.tharun.employeetaskmanagement.repository;

import com.tharun.employeetaskmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repository used to access Employee data in the database.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /*
     * Finds an Employee using the linked User ID.
     */
    Optional<Employee> findByUserId(Long userId);
}