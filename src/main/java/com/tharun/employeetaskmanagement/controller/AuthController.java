package com.tharun.employeetaskmanagement.controller;

import com.tharun.employeetaskmanagement.dto.LoginRequestDTO;
import com.tharun.employeetaskmanagement.dto.LoginResponseDTO;
import com.tharun.employeetaskmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/*
 * Controller for authentication-related API requests.
 * It receives login requests and passes them to AuthService.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /*
     * Authenticates a user using email and password.
     */
    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {

        return authService.login(requestDTO);
    }
}