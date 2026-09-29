package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.dto.EmployeeRequestDTO;
import com.tharun.employeetaskmanagement.dto.EmployeeResponseDTO;
import com.tharun.employeetaskmanagement.entity.Employee;
import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.exception.EmployeeNotFoundException;
import com.tharun.employeetaskmanagement.exception.UserNotFoundException;
import com.tharun.employeetaskmanagement.repository.EmployeeRepository;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service layer for Employee-related business logic.
 * It connects the Controller with the repositories.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            UserRepository userRepository) {

        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    /*
     * Creates an Employee and links it to an existing User.
     */
    public EmployeeResponseDTO createEmployee(
            Long userId,
            EmployeeRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId));

        Employee employee = new Employee();

        employee.setName(requestDTO.getName());
        employee.setDepartment(requestDTO.getDepartment());
        employee.setPhone(requestDTO.getPhone());
        employee.setUser(user);

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponseDTO(savedEmployee);
    }

    /*
     * Retrieves all employees and converts them to response DTOs.
     */
    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    /*
     * Retrieves one employee by ID.
     */
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));

        return convertToResponseDTO(employee);
    }

    /*
     * Updates the employee's editable details.
     */
    public EmployeeResponseDTO updateEmployee(
            Long id,
            EmployeeRequestDTO requestDTO) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));

        existingEmployee.setName(requestDTO.getName());
        existingEmployee.setDepartment(requestDTO.getDepartment());
        existingEmployee.setPhone(requestDTO.getPhone());

        Employee savedEmployee =
                employeeRepository.save(existingEmployee);

        return convertToResponseDTO(savedEmployee);
    }

    /*
     * Deactivates the User linked to the Employee.
     */
    public EmployeeResponseDTO deactivateEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));

        User user = employee.getUser();

        user.setStatus("INACTIVE");
        userRepository.save(user);

        return convertToResponseDTO(employee);
    }

    /*
     * Converts an Employee entity into a safe response DTO.
     * Only selected User information is included.
     */
    private EmployeeResponseDTO convertToResponseDTO(
            Employee employee) {

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        responseDTO.setId(employee.getId());
        responseDTO.setName(employee.getName());
        responseDTO.setDepartment(employee.getDepartment());
        responseDTO.setPhone(employee.getPhone());

        if (employee.getUser() != null) {
            responseDTO.setUserId(employee.getUser().getId());
            responseDTO.setUserEmail(employee.getUser().getEmail());
            responseDTO.setUserStatus(employee.getUser().getStatus());
        }

        return responseDTO;
    }
}