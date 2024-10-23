package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.User;

import java.util.List;

public interface UserService {
    User add(User user);

    List<User> getAll();

    User getById(String id);

    User update(String id, User user);
}
