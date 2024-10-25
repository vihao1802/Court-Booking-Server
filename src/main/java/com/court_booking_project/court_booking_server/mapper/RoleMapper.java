package com.court_booking_project.court_booking_server.mapper;

import com.court_booking_project.court_booking_server.dto.request.CreateRoleRequest;
import com.court_booking_project.court_booking_server.dto.response.RoleResponse;
import com.court_booking_project.court_booking_server.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface RoleMapper {
    Role toRole(CreateRoleRequest createRoleRequest);

    RoleResponse toRoleResponse(Role role);
}