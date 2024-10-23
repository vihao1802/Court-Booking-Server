package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User update(String id, User user) {
        User userEntity =
                userRepository.findById(id).get();

        userRepository.save(userEntity);
        return user;
    }

}
