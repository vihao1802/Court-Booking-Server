package com.court_booking_project.court_booking_server.config;

import com.court_booking_project.court_booking_server.dto.request.CreateRoleRequest;
import com.court_booking_project.court_booking_server.dto.request.CreateUserRequest;
import com.court_booking_project.court_booking_server.entity.Role;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.repository.IRoleRepository;
import com.court_booking_project.court_booking_server.repository.IUserRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IRoleService;
import com.court_booking_project.court_booking_server.service.interfaces.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                roleRepository.save(new Role("ADMIN"));
                roleRepository.save(new Role("USER"));
            }

            if(userRepository.findByEmail("admin@admin.com") == null) {
                User newAdmin = User.builder()
                        .userName("admin")
                        .email("admin@admin.com")
                        .role(roleRepository.findByRoleName("ADMIN"))
                        .password(passwordEncoder.encode("admin"))
                        .build();
                userRepository.save(newAdmin);
                log.warn("Admin account created with email:admin@admin, password:admin");
            }
        };
    }
}
