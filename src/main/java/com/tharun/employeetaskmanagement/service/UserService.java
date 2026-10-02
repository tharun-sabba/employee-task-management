package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.dto.UserRequestDTO;
import com.tharun.employeetaskmanagement.dto.UserResponseDTO;
import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.exception.UserNotFoundException;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service layer for User-related business logic.
 * Spring manages this class and uses it for application logic.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
     * Constructor injection is used to provide the repository
     * and password encoder to this service.
     */
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * Creates a new User from the request DTO.
     * The password is hashed before being stored in MySQL.
     */
    public UserResponseDTO saveUser(UserRequestDTO requestDTO) {

        // Convert the request DTO into a User entity.
        User user = convertToEntity(requestDTO);

        // Save the User entity in the database.
        User savedUser = userRepository.save(user);

        // Convert the saved entity into a safe response DTO.
        return convertToResponseDTO(savedUser);
    }

    /*
     * Retrieves all users from the database
     * and converts them into response DTOs.
     */
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    /*
     * Retrieves a user by ID.
     * Throws an exception when the user does not exist.
     */
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        return convertToResponseDTO(user);
    }

    /*
     * Updates an existing User.
     * The new password is also hashed before storage.
     */
    public UserResponseDTO updateUser(
            Long id,
            UserRequestDTO requestDTO) {

        // Find the existing user.
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        // Update the user's basic information.
        existingUser.setEmail(requestDTO.getEmail());

        /*
         * Hash the new password before saving it.
         * The raw password is never stored directly.
         */
        existingUser.setPassword(
                passwordEncoder.encode(requestDTO.getPassword())
        );

        existingUser.setRole(requestDTO.getRole());
        existingUser.setStatus(requestDTO.getStatus());

        // Save the updated User.
        User savedUser = userRepository.save(existingUser);

        // Return a safe response DTO.
        return convertToResponseDTO(savedUser);
    }

    /*
     * Converts a User entity into a UserResponseDTO.
     * Password is intentionally excluded from the response.
     */
    private UserResponseDTO convertToResponseDTO(User user) {

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setStatus(user.getStatus());

        return responseDTO;
    }

    /*
     * Converts a UserRequestDTO into a User entity.
     * The password is hashed before the entity is returned.
     */
    private User convertToEntity(UserRequestDTO requestDTO) {

        User user = new User();

        user.setEmail(requestDTO.getEmail());

        /*
         * Convert the raw password into a BCrypt hash.
         */
        user.setPassword(
                passwordEncoder.encode(requestDTO.getPassword())
        );

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