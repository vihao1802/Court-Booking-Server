package com.court_booking_project.court_booking_server.mapper;

import com.court_booking_project.court_booking_server.dto.request.authentication.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.response.authentication.UserResponse;
import com.court_booking_project.court_booking_server.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserRequest createUserRequest);

    UserResponse toUserResponse(User user);
}
