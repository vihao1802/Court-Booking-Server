package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.service.interfaces.IForgotPasswordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RequestMapping("/api/v1/forgot-password")
public class ForgotPasswordController {
    IForgotPasswordService forgotPasswordService;

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String email){
        log.info("Email: {}", email);
        forgotPasswordService.verifyEmail(email);
        return new ResponseEntity<>("OTP is sent to your email!", HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam int otp){
        forgotPasswordService.verifyOtp(email, otp);
        return new ResponseEntity<>("OTP is verified!", HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String confirmPassword, @RequestParam int otp){
        forgotPasswordService.changePassword(email, newPassword, confirmPassword, otp);
        return new ResponseEntity<>("Password is changed!", HttpStatus.OK);
    }
}
