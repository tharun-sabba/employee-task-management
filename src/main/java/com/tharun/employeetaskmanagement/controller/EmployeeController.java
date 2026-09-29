package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.dto.EmployeeRequestDTO;
import com.tharun.employeetaskmanagement.dto.EmployeeResponseDTO;
import com.tharun.employeetaskmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller for handling Employee-related API requests.
 * It receives HTTP requests and passes the work to EmployeeService.
 */
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /*
     * Creates a new Employee for an existing User.
     */
    @PostMapping("/api/employees/{userId}")
    public EmployeeResponseDTO createEmployee(
            @PathVariable Long userId,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        return employeeService.createEmployee(userId, requestDTO);
    }

    /*
     * Retrieves all employees.
     */
    @GetMapping("/api/employees")
    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeService.getAllEmployees();
    }

    /*
     * Retrieves one employee using the employee ID.
     */
    @GetMapping("/api/employees/{id}")
    public EmployeeResponseDTO getEmployeeById(
            @PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }

    /*
     * Updates an existing employee.
     */
    @PutMapping("/api/employees/{id}")
    public EmployeeResponseDTO updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        return employeeService.updateEmployee(id, requestDTO);
    }

    /*
     * Deactivates the User linked to the Employee.
     */
    @PatchMapping("/api/employees/{id}/status")
    public EmployeeResponseDTO deactivateEmployee(
            @PathVariable Long id) {

        return employeeService.deactivateEmployee(id);
    }
}