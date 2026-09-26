package com.tharun.employeetaskmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/*
 * Represents an employee's profile information.
 * Each Employee is linked to one User account.
 */
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String department;

    private String phone;

    /*
     * Connects this Employee to its User account.
     * user_id will be stored as a foreign key in the employee table.
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Returns the employee ID.
    public Long getId() {
        return id;
    }

    // Sets the employee ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Returns the employee name.
    public String getName() {
        return name;
    }

    // Sets the employee name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the employee department.
    public String getDepartment() {
        return department;
    }

    // Sets the employee department.
    public void setDepartment(String department) {
        this.department = department;
    }

    // Returns the employee phone number.
    public String getPhone() {
        return phone;
    }

    // Sets the employee phone number.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns the User account linked to this employee.
    public User getUser() {
        return user;
    }

    // Sets the User account for this employee.
    public void setUser(User user) {
        this.user = user;
    }
}