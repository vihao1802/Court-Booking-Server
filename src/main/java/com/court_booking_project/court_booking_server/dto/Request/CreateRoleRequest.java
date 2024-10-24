package com.court_booking_project.court_booking_server.dto.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoleRequest {
    private String roleName;
}
