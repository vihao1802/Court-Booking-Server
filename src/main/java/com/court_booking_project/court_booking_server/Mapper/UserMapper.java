package com.court_booking_project.court_booking_server.Mapper;

import com.court_booking_project.court_booking_server.dto.Request.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.Response.UserResponse;
import com.court_booking_project.court_booking_server.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserRequest createUserRequest);

    UserResponse toUserResponse(User user);
}
