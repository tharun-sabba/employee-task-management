package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.dto.UserRequestDTO;
import com.tharun.employeetaskmanagement.dto.UserResponseDTO;
import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
/*
 * Controller for handling User-related API requests.
 * It receives HTTP requests and passes the work to UserService.
 */
@RestController
public class UserController {

    private final UserService userService;

    /*
     * Constructor injection.
     * Spring automatically provides the UserService object.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Test endpoint used to check whether the User controller is working.
     */
    @GetMapping("/api/users/test")
    public String testUserApi() {

        return "User API is working";
    }

    /*
     * Creates a new User.
     * The request body is validated before reaching the Service layer.
     */
    @PostMapping("/api/users")
    public UserResponseDTO createUser(
            @Valid @RequestBody UserRequestDTO requestDTO) {

        // Send the validated request DTO to the Service layer.
        return userService.saveUser(requestDTO);
    }

    /*
     * Retrieves all users from the database.
     */
    @GetMapping("/api/users")
    public List<UserResponseDTO> getAllUsers() {

        // Ask the Service layer to retrieve all users.
        return userService.getAllUsers();
    }

    /*
     * Retrieves a single User using the ID from the URL.
     */
    @GetMapping("/api/users/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Long id) {

        // Send the ID to the Service layer.
        return userService.getUserById(id);
    }
    /*
     * Updates an existing User.
     * The request body is validated before reaching the Service layer.
     */
    @PutMapping("/api/users/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO requestDTO) {

        // Send the validated request data to the Service layer.
        return userService.updateUser(id, requestDTO);
    }
}