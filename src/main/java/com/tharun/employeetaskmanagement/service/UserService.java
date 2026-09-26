package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Marks this class as a Service component.
// Spring will manage this class and use it for business logic.
@Service
public class UserService {

    // Repository used to access User data in the database.
    private final UserRepository userRepository;

    // Constructor injection: Spring provides the UserRepository object here.
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Saves a User to the database through the repository.
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /*
      Retrieves all users from the database.
      The repository provides the findAll() method through JpaRepository.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    /*
      Retrieves a user by their ID.
      findById() is provided by JpaRepository.
     */
    public User getUserById(Long id) {

        // Search the database for the user with the given ID.
        return userRepository.findById(id)
                .orElse(null);
    }
    /*
     * Updates an existing user using the given ID.
     * First finds the user, updates its values, and then saves it.
     */
    public User updateUser(Long id, User updatedUser) {

        // Find the existing user in the database.
        User existingUser = userRepository.findById(id)
                .orElse(null);

        // If the user does not exist, return null for now.
        if (existingUser == null) {
            return null;
        }

        // Update the existing user's details.
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setStatus(updatedUser.getStatus());

        // Save the updated user back to the database.
        return userRepository.save(existingUser);
    }
}

/*@Service
    ↓
This is our business logic layer.

UserRepository
    ↓
Used to communicate with the database.

saveUser()
    ↓
Receives a User
    ↓
Sends it to Repository
    ↓
Repository saves it in MySQL*/