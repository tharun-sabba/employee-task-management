package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.entity.Employee;
import com.tharun.employeetaskmanagement.repository.EmployeeRepository;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.tharun.employeetaskmanagement.exception.UserNotFoundException;
import com.tharun.employeetaskmanagement.entity.User;
import java.util.List;
import com.tharun.employeetaskmanagement.exception.EmployeeNotFoundException;
/*
 * Service layer for Employee-related business logic.
 * It uses both EmployeeRepository and UserRepository
 * because an Employee is linked to an existing User account.
 */
@Service
public class EmployeeService {

    /*
     * Repository used to access Employee data in the database.
     */
    private final EmployeeRepository employeeRepository;

    /*
     * Repository used to find the User account
     * that will be linked to the Employee.
     */
    private final UserRepository userRepository;

    /*
     * Constructor injection.
     * Spring provides both repository objects automatically.
     */
    public EmployeeService(
            EmployeeRepository employeeRepository,
            UserRepository userRepository) {

        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }
    /*
     * Creates an Employee profile and links it to an existing User account.
     * The User must exist before the Employee can be created.
     */
    public Employee createEmployee(Long userId, Employee employee) {

        // Find the existing User account.
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));

        // Link the User account to the Employee profile.
        employee.setUser(user);

        // Save the Employee profile in the database.
        return employeeRepository.save(employee);
    }
    /*
     * Retrieves all employees from the database.
     * The repository provides the findAll() method through JpaRepository.
     */
    public List<Employee> getAllEmployees() {

        // Fetch all employee records from the database.
        return employeeRepository.findAll();
    }
    /*
     * Retrieves an employee by their ID.
     * If the employee does not exist, an exception is thrown.
     */
    public Employee getEmployeeById(Long id) {

        // Search the database for the employee with the given ID.
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));
    }
    /*
     * Updates an existing employee using the given employee ID.
     * First finds the employee, updates its details, and saves it.
     */
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        // Find the existing employee in the database.
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));

        // Update the employee's profile details.
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setPhone(updatedEmployee.getPhone());

        // Save the updated employee.
        return employeeRepository.save(existingEmployee);
    }
    /*
     * Deactivates an employee by changing the status
     * of the linked User account to INACTIVE.
     */
    public Employee deactivateEmployee(Long employeeId) {

        // Find the employee using its ID.
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + employeeId));

        // Get the User account linked to this employee.
        User user = employee.getUser();

        // Change the account status to INACTIVE.
        user.setStatus("INACTIVE");

        // Save the updated User account.
        userRepository.save(user);

        // Return the employee with its updated User status.
        return employee;
    }

}