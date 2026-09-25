package com.tharun.employeetaskmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * Represents a User in the application.
 * This class is mapped to the "user" table in MySQL.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String role;

    private String status;

    // Returns the user's ID.
    public Long getId() {
        return id;
    }

    // Sets the user's ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Returns the user's email.
    public String getEmail() {
        return email;
    }

    // Sets the user's email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns the user's password.
    public String getPassword() {
        return password;
    }

    // Sets the user's password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Returns the user's role.
    public String getRole() {
        return role;
    }

    // Sets the user's role.
    public void setRole(String role) {
        this.role = role;
    }

    // Returns the user's status.
    public String getStatus() {
        return status;
    }

    // Sets the user's status.
    public void setStatus(String status) {
        this.status = status;
    }
}