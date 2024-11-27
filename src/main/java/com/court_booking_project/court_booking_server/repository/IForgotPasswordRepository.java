package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.entity.ForgotPassword;
import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IForgotPasswordRepository extends JpaRepository<ForgotPassword, String> {
    Optional<ForgotPassword> findByUser(User user);
    Optional<ForgotPassword> findByOtpAndUser(int otp, User user);
}
