package com.tharun.employeetaskmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*
 * DTO used to receive User data from API requests.
 * Validation rules are applied to the incoming data.
 */
public class UserRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Status is required")
    private String status;

    /*
     * Returns the user's email.
     */
    public String getEmail() {
        return email;
    }

    /*
     * Sets the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Returns the user's password.
     */
    public String getPassword() {
        return password;
    }

    /*
     * Sets the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * Returns the user's role.
     */
    public String getRole() {
        return role;
    }

    /*
     * Sets the user's role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /*
     * Returns the user's status.
     */
    public String getStatus() {
        return status;
    }

    /*
     * Sets the user's status.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}