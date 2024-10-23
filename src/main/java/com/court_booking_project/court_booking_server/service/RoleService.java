package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();
    Role get(String id);
    void add(Role role);
}
