package com.meenakshiscreens.meenakshiscreensbackend.entity.request;

import com.meenakshiscreens.meenakshiscreensbackend.enums.Role;

import javax.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String userPass;

    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    private String lastName;

    private String contactNo;

    private Role role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
