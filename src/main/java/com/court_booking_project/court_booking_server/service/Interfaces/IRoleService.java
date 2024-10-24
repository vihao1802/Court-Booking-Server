package com.court_booking_project.court_booking_server.service.Interfaces;

import com.court_booking_project.court_booking_server.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> getAll();
    Role getByName(String roleName);
    Role get(String id);
    void add(Role role);
}
