package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  Controller for handling User-related API requests.
  It receives HTTP requests and passes the work to the Service layer.
 */
@RestController
public class UserController {

    private final UserService userService;

    /*
      Constructor injection.
      Spring automatically provides the UserService object.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
      Test endpoint used to check whether the User controller is working.
     */
    @GetMapping("/api/users/test")
    public String testUserApi() {
        return "User API is working";
    }

    /*
     Creates a new User.
     @RequestBody converts the JSON request into a User object.
     */
    @PostMapping("/api/users")
    public User createUser(@RequestBody User user) {

        // Send the User to the Service layer for saving.

        return userService.saveUser(user);

    }
    /*
     * Returns all users stored in the database.
     * Calls the Service layer to retrieve the users.
     */
    @GetMapping("/api/users")
    public List<User> getAllUsers() {

        // Ask the Service to retrieve all users.
        return userService.getAllUsers();
    }
}