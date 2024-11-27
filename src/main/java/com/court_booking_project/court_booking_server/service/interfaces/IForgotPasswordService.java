package com.court_booking_project.court_booking_server.service.interfaces;

public interface IForgotPasswordService {
    void  verifyEmail(String email);
    void verifyOtp(String email, int otp);
    void changePassword(String email, String newPassword, String confirmPassword, int otp);
}
