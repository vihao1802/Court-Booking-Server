package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "users")
public class User {

    @Id
    @UuidGenerator
    private String id;
    private String userName;
    @Column(name="email", unique = true,columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String email;
    private String password;
    private String phoneNumber;
    private Date dayOfBirth;
    private Date createdAt;
    private String location;
    private int isDisabled;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
    private Role role;


}
