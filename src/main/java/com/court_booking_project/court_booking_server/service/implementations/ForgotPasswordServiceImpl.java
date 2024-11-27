package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.entity.ForgotPassword;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import com.court_booking_project.court_booking_server.repository.IForgotPasswordRepository;
import com.court_booking_project.court_booking_server.repository.IUserRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements IForgotPasswordService {
    private final MailerService mailerService;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IForgotPasswordRepository forgotPasswordRepository;

    public void  verifyEmail(String email) {
        var user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        int otp = (int) (Math.random() * 9000) + 1000;

        ForgotPassword forgotPassword = forgotPasswordRepository.findByUser(user.get())
                .orElse(null);

        if (forgotPassword != null) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
        } else {
            forgotPassword = ForgotPassword.builder()
                    .otp(otp)
                    .expirationTime(new Date(System.currentTimeMillis() + 180 * 1000))
                    .user(user.get())
                    .build();
        }

        mailerService.sendEmail(email,"ForgotPassword OTP", "This is the OTP for your forgot password request: " + otp);
        forgotPasswordRepository.save(forgotPassword);

    }
    public void verifyOtp(String email, int otp) {
        var user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user.get())
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));

        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
    }
    public void changePassword(String email, String newPassword, String confirmPassword, int otp) {

        verifyOtp(email, otp);

        if (!Objects.equals(newPassword, confirmPassword)) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

}
