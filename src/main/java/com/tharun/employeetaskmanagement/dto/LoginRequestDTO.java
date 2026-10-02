package com.tharun.employeetaskmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
 * DTO used to receive login credentials from the client.
 */
public class LoginRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    /*
     * Returns the login email.
     */
    public String getEmail() {
        return email;
    }

    /*
     * Sets the login email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Returns the login password.
     */
    public String getPassword() {
        return password;
    }

    /*
     * Sets the login password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}