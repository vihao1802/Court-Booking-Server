package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.temp_request.role.CreateRoleRequest;
import com.court_booking_project.court_booking_server.dto.response.role.RoleResponse;
import com.court_booking_project.court_booking_server.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> getAll();
    Role getByName(String roleName);
    Role get(String id);
    RoleResponse add(CreateRoleRequest createRoleRequest);
}
