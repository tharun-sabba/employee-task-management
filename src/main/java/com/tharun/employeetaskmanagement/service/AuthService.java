package com.tharun.employeetaskmanagement.service;

import com.tharun.employeetaskmanagement.dto.LoginRequestDTO;
import com.tharun.employeetaskmanagement.dto.LoginResponseDTO;
import com.tharun.employeetaskmanagement.entity.User;
import com.tharun.employeetaskmanagement.exception.InvalidCredentialsException;
import com.tharun.employeetaskmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * Service layer for authentication-related business logic.
 * It verifies user credentials and generates a JWT after login.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /*
     * Constructor injection provides the required dependencies.
     */
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /*
     * Verifies the user's email and password.
     * A JWT is generated after successful authentication.
     */
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {

        /*
         * Find the user using the email provided during login.
         */
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"));

        /*
         * Compare the raw password with the BCrypt hash
         * stored in the database.
         */
        boolean passwordMatches = passwordEncoder.matches(
                requestDTO.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException(
                    "Invalid email or password");
        }

        /*
         * Inactive users are not allowed to log in.
         */
        if ("INACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new IllegalArgumentException(
                    "User account is inactive");
        }

        /*
         * Generate a JWT containing the user's ID, email, and role.
         */
        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        /*
         * Build the login response.
         * The password is never returned.
         */
        LoginResponseDTO responseDTO = new LoginResponseDTO();

        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setStatus(user.getStatus());
        responseDTO.setMessage("Login successful");
        responseDTO.setToken(token);

        return responseDTO;
    }
}