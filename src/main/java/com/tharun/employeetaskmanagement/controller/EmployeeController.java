package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.entity.Employee;
import com.tharun.employeetaskmanagement.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.PatchMapping;

/*
 * Controller for handling Employee-related API requests.
 * It receives HTTP requests and passes the work to EmployeeService.
 */
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    /*
     * Constructor injection.
     * Spring provides the EmployeeService object automatically.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /*
     * Creates an Employee profile for an existing User.
     * The user ID is taken from the URL.
     */
    @PostMapping("/api/employees/{userId}")
    public Employee createEmployee(
            @PathVariable Long userId,
            @RequestBody Employee employee) {

        // Send the user ID and employee details to the Service layer.
        return employeeService.createEmployee(userId, employee);
    }
    /*
     * Retrieves all employees from the database.
     * Calls the Service layer to fetch the employee records.
     */
    @GetMapping("/api/employees")
    public List<Employee> getAllEmployees() {

        // Ask the Service layer for all employees.
        return employeeService.getAllEmployees();
    }
    /*
     * Retrieves a single employee using the employee ID.
     * The ID is taken from the URL path.
     */
    @GetMapping("/api/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {

        // Send the employee ID to the Service layer.
        return employeeService.getEmployeeById(id);
    }
    /*
     * Updates an existing employee using the employee ID from the URL.
     * The updated employee details come from the request body.
     */
    @PutMapping("/api/employees/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee updatedEmployee) {

        // Send the employee ID and updated details to the Service layer.
        return employeeService.updateEmployee(id, updatedEmployee);
    }
    /*
     * Deactivates an employee's User account.
     * The employee ID is taken from the URL.
     */
    @PatchMapping("/api/employees/{id}/status")
    public Employee deactivateEmployee(@PathVariable Long id) {

        // Send the employee ID to the Service layer.
        return employeeService.deactivateEmployee(id);
    }
}