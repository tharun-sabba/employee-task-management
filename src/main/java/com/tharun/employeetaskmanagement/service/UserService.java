package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.tharun.employeetaskmanagement.exception.UserNotFoundException;
import com.tharun.employeetaskmanagement.dto.UserResponseDTO;
import com.tharun.employeetaskmanagement.dto.UserRequestDTO;

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

    /*
     * Creates a new User from the request DTO
     * and returns a safe response DTO.
     */
    public UserResponseDTO saveUser(UserRequestDTO requestDTO) {

        // Convert the API request into a database entity.
        User user = convertToEntity(requestDTO);

        // Save the User entity in the database.
        User savedUser = userRepository.save(user);

        // Convert the saved entity into a safe API response.
        return convertToResponseDTO(savedUser);
    }

    /*
     * Retrieves all users and converts them to response DTOs.
     */
    public List<UserResponseDTO> getAllUsers() {

        // Fetch all users from the database.
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }
    /*
     * Retrieves a user by ID and returns a safe response DTO.
     */
    public UserResponseDTO getUserById(Long id) {

        // Find the user in the database.
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        // Convert the entity to a response DTO.
        return convertToResponseDTO(user);
    }
    /*
     * Updates an existing User using data from the request DTO.
     * Returns a safe response DTO without the password.
     */
    public UserResponseDTO updateUser(
            Long id,
            UserRequestDTO requestDTO) {

        // Find the existing user in the database.
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        // Update the user's details from the request DTO.
        existingUser.setEmail(requestDTO.getEmail());
        existingUser.setPassword(requestDTO.getPassword());
        existingUser.setRole(requestDTO.getRole());
        existingUser.setStatus(requestDTO.getStatus());

        // Save the updated user.
        User savedUser = userRepository.save(existingUser);

        // Convert the entity into a safe response DTO.
        return convertToResponseDTO(savedUser);
    }
    /*
     * Converts a User entity into a UserResponseDTO.
     * Password is intentionally not copied to the DTO.
     */
    private UserResponseDTO convertToResponseDTO(User user) {

        UserResponseDTO responseDTO = new UserResponseDTO();

        // Copy only the fields that are safe to send to the client.
        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setStatus(user.getStatus());

        return responseDTO;
    }
    /*
     * Converts a UserRequestDTO into a User entity.
     * This keeps API input separate from the database entity.
     */
    private User convertToEntity(UserRequestDTO requestDTO) {

        User user = new User();

        // Copy the request data into the User entity.
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setRole(requestDTO.getRole());
        user.setStatus(requestDTO.getStatus());

        return user;
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