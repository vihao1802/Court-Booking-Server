package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table( name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @UuidGenerator
    @Column(name = "id",nullable = false)
    String id;

    @Column(name = "role_name",nullable = false)
    String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
