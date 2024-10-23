package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.Date;
import java.util.UUID;

@Entity
@Table( name = "users")
public class User {

    @Id
    @UuidGenerator
    private String id;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private Date createdAt;
    private int isDisabled;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
    private Role role;

    public User() {
    }

    public User(int isDisabled, Date createdAt, String phoneNumber, String password, String email, String userName) {
        this.id = UUID.randomUUID().toString();
        this.isDisabled = isDisabled;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                ", isDisabled=" + isDisabled +
                ", role=" + role +
                '}';
    }
}
