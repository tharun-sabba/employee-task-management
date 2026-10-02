package com.tharun.employeetaskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Security configuration for the application.
 * Provides password hashing and basic API security configuration.
 */
@Configuration
public class SecurityConfig {

    /*
     * Creates the BCrypt password encoder.
     * This is used to hash passwords before storing them.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Configures HTTP security for the application.
     *
     * Authentication and role-based authorization will be added
     * in the next security phase.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                /*
                 * CSRF is disabled because this application currently
                 * exposes REST APIs rather than browser form submissions.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Allow all current APIs temporarily.
                 * Authentication and authorization will be enforced later.
                 */
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}