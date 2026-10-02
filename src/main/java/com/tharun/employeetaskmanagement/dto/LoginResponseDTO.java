package com.tharun.employeetaskmanagement.dto;

/*
 * DTO used to send login information back to the client.
 * The password is never included in the response.
 */
public class LoginResponseDTO {

    private Long id;
    private String email;
    private String role;
    private String status;
    private String message;

    /*
     * Returns the user ID.
     */
    public Long getId() {
        return id;
    }

    /*
     * Sets the user ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /*
     * Returns the user email.
     */
    public String getEmail() {
        return email;
    }

    /*
     * Sets the user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Returns the user role.
     */
    public String getRole() {
        return role;
    }

    /*
     * Sets the user role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /*
     * Returns the user status.
     */
    public String getStatus() {
        return status;
    }

    /*
     * Sets the user status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /*
     * Returns the login message.
     */
    public String getMessage() {
        return message;
    }

    /*
     * Sets the login message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}