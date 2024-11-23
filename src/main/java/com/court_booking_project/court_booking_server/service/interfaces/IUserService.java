package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.authentication.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.request.user.UpdatePasswordRequest;
import com.court_booking_project.court_booking_server.dto.request.user.DisableUserRequest;
import com.court_booking_project.court_booking_server.dto.request.user.UpdateUserRequest;
import com.court_booking_project.court_booking_server.dto.response.authentication.UserResponse;
import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface IUserService {
    UserResponse add(CreateUserRequest createUserRequest);

    List<User> getAll();
    Page<User> getUsers(String keyword,Pageable pageable);
    UserResponse getMyInfo();
    UserResponse getById(String id);

    UserResponse findByEmail(String email);
    UserResponse update(UpdateUserRequest updateUserRequest);
    UserResponse updateProfilePicture(MultipartFile imageFile);
    void updatePassword(UpdatePasswordRequest updatePasswordRequest);
    Integer getTotalNewUser(Date startDate, Date endDate);
    UserResponse disableUser(String id, DisableUserRequest request);
}
