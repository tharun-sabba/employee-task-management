package com.tharun.employeetaskmanagement.repository;

import com.tharun.employeetaskmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository used to access Employee data in the database.
 * JpaRepository provides common CRUD operations automatically.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}