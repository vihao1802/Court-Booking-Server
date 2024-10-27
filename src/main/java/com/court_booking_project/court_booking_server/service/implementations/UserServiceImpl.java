package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.constant.PredefineRole;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import com.court_booking_project.court_booking_server.mapper.UserMapper;
import com.court_booking_project.court_booking_server.dto.request.authentication.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.response.authentication.UserResponse;
import com.court_booking_project.court_booking_server.entity.Role;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.repository.IUserRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements IUserService {

    IUserRepository userRepository;
    UserMapper userMapper;
    RoleServiceImpl roleServiceImpl;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse add(CreateUserRequest createUserRequest) {
        var checkEmail = userRepository.findByEmail(createUserRequest.getEmail());

        if(checkEmail.isPresent())
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(createUserRequest);
        user.setCreatedAt(Date.from(java.time.Instant.now()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleServiceImpl.getByName(PredefineRole.USER_ROLE);
        user.setRole(userRole);

        try{
            userRepository.save(user);
        }catch(DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();

        String email = context.getAuthentication().getName();
        log.info("Email: {}", email);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
            throw new AppException(ErrorCode.UNAUTHORIZED);

        return userMapper.toUserResponse(user.get());
    }

    @Override
    public UserResponse getById(String id) {
        var user = userRepository.findById(id);
        if(user.isEmpty())
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        return userMapper.toUserResponse(user.get()) ;
    }

    @Override
    public UserResponse findByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        return userMapper.toUserResponse(user.get()) ;
    }


    @Override
    public UserResponse update(String id, User user) {
        var userEntity = userRepository.findById(id);

        if (userEntity.isEmpty())
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        userRepository.save(userEntity.get());
        return userMapper.toUserResponse(userEntity.get());
    }

}
