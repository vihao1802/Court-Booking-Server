package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.authentication.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.response.authentication.UserResponse;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.service.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.court_booking_project.court_booking_server.constant.PredefineRole;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    @Autowired
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public UserResponse Register(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return  userService.add(createUserRequest);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") String id) {
        var user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/my-info")
    public ResponseEntity<UserResponse> getMyInfo() {
        var user = userService.getMyInfo();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") String id,
                                           @RequestBody User user) {
        userService.update(id,user);
        return ResponseEntity.noContent().build();
    }
}
