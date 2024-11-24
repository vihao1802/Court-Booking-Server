package com.court_booking_project.court_booking_server.controller;

import com.cloudinary.Cloudinary;
import com.court_booking_project.court_booking_server.constant.CloudinaryFolder;
import com.court_booking_project.court_booking_server.dto.request.authentication.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.request.user.UpdatePasswordRequest;
import com.court_booking_project.court_booking_server.dto.request.user.DisableUserRequest;

import com.court_booking_project.court_booking_server.dto.request.user.UpdateUserRequest;
import com.court_booking_project.court_booking_server.dto.response.CloudinaryResponse;
import com.court_booking_project.court_booking_server.dto.response.authentication.UserResponse;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.service.interfaces.IMediaService;
import com.court_booking_project.court_booking_server.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.court_booking_project.court_booking_server.constant.PredefineRole;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RequestMapping("/api/v1/")
public class UserController {
    IUserService userService;

    @PostMapping("/users/register")
    public UserResponse Register(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.add(createUserRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ("/users/paginated")
    public Page<User> getUsers(@RequestParam(value = "search", required = false) String keyword,Pageable pageable) {
        return userService.getUsers(keyword,pageable);
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

    @PutMapping("/users/update")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        userService.update(updateUserRequest);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/users/update/profile-image")
    public ResponseEntity<UserResponse> uploadImage(@RequestParam MultipartFile imageFile) {
        return new ResponseEntity<UserResponse>(userService.updateProfilePicture(imageFile), HttpStatus.OK);
    }

    @PutMapping("/users/update/password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordRequest updateUserRequest) {
        userService.updatePassword(updateUserRequest);
        return ResponseEntity.noContent().build();
    }
    @PutMapping ("/users/{id}/disable-user")
    public ResponseEntity<UserResponse> disableUser(@PathVariable String id, @RequestBody DisableUserRequest disableUserRequest) {
        return  new ResponseEntity<UserResponse>(userService.disableUser(id, disableUserRequest),HttpStatus.OK);
    }

}
