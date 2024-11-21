package com.court_booking_project.court_booking_server.config;

import com.court_booking_project.court_booking_server.constant.PredefineRole;
import com.court_booking_project.court_booking_server.entity.Role;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.repository.IRoleRepository;
import com.court_booking_project.court_booking_server.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(IRoleRepository roleRepository, IUserRepository userRepository) {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
                roleRepository.save(new Role(PredefineRole.ADMIN_ROLE));
                roleRepository.save(new Role(PredefineRole.USER_ROLE));
            }

            if(userRepository.findByEmail("admin@admin.com").isEmpty()) {
                User newAdmin = User.builder()
                        .userName("admin")
                        .email("admin@admin.com")
                        .role(roleRepository.findByRoleName("ADMIN"))
                        .password(passwordEncoder.encode("admin"))
                        .phoneNumber("0123456789")
                        .createdAt(java.util.Date.from(java.time.Instant.now()))
                        .profileImage("https://res.cloudinary.com/dxlnrizu7/image/upload/v1728888785/cld-sample-5.jpg")
                        .build();
                userRepository.save(newAdmin);
                log.warn("Admin account created with email:admin@admin, password:admin");
            }
        };
    }
}
