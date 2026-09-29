package com.tharun.employeetaskmanagement.dto;

/*
 * DTO used to send Employee data back to the client.
 * Only the required User details are included.
 */
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String department;
    private String phone;

    private Long userId;
    private String userEmail;
    private String userStatus;

    /*
     * Returns the employee ID.
     */
    public Long getId() {
        return id;
    }

    /*
     * Sets the employee ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

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

    /*
     * Returns the linked User ID.
     */
    public Long getUserId() {
        return userId;
    }

    /*
     * Sets the linked User ID.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /*
     * Returns the linked User email.
     */
    public String getUserEmail() {
        return userEmail;
    }

    /*
     * Sets the linked User email.
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /*
     * Returns the linked User status.
     */
    public String getUserStatus() {
        return userStatus;
    }

    /*
     * Sets the linked User status.
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}