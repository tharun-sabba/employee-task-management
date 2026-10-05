package com.tharun.employeetaskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Security configuration for the application.
 *
 * Configures password hashing, JWT authentication,
 * and role-based authorization.
 */
@Configuration
public class SecurityConfig {

    /*
     * Creates the BCrypt password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Converts the custom "role" JWT claim into
     * Spring Security roles.
     *
     * ADMIN    -> ROLE_ADMIN
     * EMPLOYEE -> ROLE_EMPLOYEE
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        // Read roles from our custom JWT claim.
        authoritiesConverter.setAuthoritiesClaimName("role");

        // Add ROLE_ so hasRole("ADMIN") works.
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return converter;
    }

    /*
     * Configures authentication and authorization rules.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                /*
                 * Disable CSRF for this REST API.
                 */
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        /*
                         * Employees can view only their own tasks.
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tasks/my"
                        ).hasRole("EMPLOYEE")

                        /*
                         * Login must be publicly accessible.
                         */
                        .requestMatchers(
                                "/api/auth/login"
                        ).permitAll()

                        /*
                         * Only ADMIN can manage Users.
                         */
                        .requestMatchers(
                                "/api/users/**"
                        ).hasRole("ADMIN")

                        /*
                         * Only ADMIN can manage Employees.
                         */
                        .requestMatchers(
                                "/api/employees/**"
                        ).hasRole("ADMIN")

                        /*
                         * Only ADMIN can create tasks.
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/tasks/*"
                        ).hasRole("ADMIN")

                        /*
                         * Only ADMIN can view all tasks.
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tasks"
                        ).hasRole("ADMIN")

                        /*
                         * Only Admin can request tasks for a specific employee ID.
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tasks/employee/**"
                        ).hasRole("ADMIN")

                        /*
                         * Only ADMIN can review tasks.
                         */
                        .requestMatchers(
                                "/api/tasks/*/reviews"
                        ).hasRole("ADMIN")

                        /*
                         * EMPLOYEE can update task status.
                         */
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/tasks/*/status"
                        ).hasRole("EMPLOYEE")

                        /*
                         * EMPLOYEE can submit tasks.
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/tasks/*/submit"
                        ).hasRole("EMPLOYEE")

                        /*
                         * All remaining requests require
                         * a valid authenticated JWT.
                         */
                        .anyRequest().authenticated()
                )

                /*
                 * Enable JWT Bearer token authentication.
                 */
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter()
                                )
                        )
                );

        return http.build();
    }
}