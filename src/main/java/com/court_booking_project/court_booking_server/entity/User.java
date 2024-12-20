package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "user_name")
    String userName;

    @Column(name="email", unique = true,columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String email;

    @Column(name = "password",nullable = false)
    String password;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "gender")
    Boolean gender = true;

    @Column(name = "day_of_birth")
    LocalDate dayOfBirth;

    @Column(name="profile_image",nullable = false)
    String profileImage;

    @Column(name = "created_at")
    Date createdAt;

    @Column(name = "location")
    String location;

    @Column(name = "is_disabled",nullable = false)
    int isDisabled = 0;

    @Column(name = "is_changed_password")
    Boolean isChangedPassword = true;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
    Role role;
}
