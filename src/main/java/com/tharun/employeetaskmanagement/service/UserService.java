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
     * Retrieves all users from the database.
     * The repository provides the findAll() method through JpaRepository.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
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