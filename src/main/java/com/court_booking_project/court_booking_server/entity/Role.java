package com.court_booking_project.court_booking_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table( name = "roles")
public class Role {

    @Id
    @UuidGenerator
    private String id;

    private String roleName;
    public Role() {

    }
    public Role(String roleName) {
        this.id = UUID.randomUUID().toString();
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
