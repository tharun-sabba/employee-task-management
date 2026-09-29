package com.tharun.employeetaskmanagement.dto;

import jakarta.validation.constraints.NotBlank;

/*
 * DTO used to receive Employee data from API requests.
 * Validation rules are applied to the incoming data.
 */
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Phone is required")
    private String phone;

    /*
     * Returns the employee name.
     */
    public String getName() {
        return name;
    }

    /*
     * Sets the employee name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Returns the employee department.
     */
    public String getDepartment() {
        return department;
    }

    /*
     * Sets the employee department.
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /*
     * Returns the employee phone number.
     */
    public String getPhone() {
        return phone;
    }

    /*
     * Sets the employee phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}