package com.court_booking_project.court_booking_server.service.Interfaces;

import com.court_booking_project.court_booking_server.dto.Request.CreateRoleRequest;
import com.court_booking_project.court_booking_server.dto.Response.RoleResponse;
import com.court_booking_project.court_booking_server.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> getAll();
    Role getByName(String roleName);
    Role get(String id);
    RoleResponse add(CreateRoleRequest createRoleRequest);
}
