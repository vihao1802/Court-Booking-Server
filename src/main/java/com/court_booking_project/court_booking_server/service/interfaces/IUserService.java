package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.response.UserResponse;
import com.court_booking_project.court_booking_server.entity.User;

import java.util.List;

public interface IUserService {
    UserResponse add(CreateUserRequest createUserRequest);

    List<User> getAll();

    User getById(String id);

    User findByEmail(String email);
    User update(String id, User user);
}
