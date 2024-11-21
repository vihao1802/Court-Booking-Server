package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.constant.CloudinaryFolder;
import com.court_booking_project.court_booking_server.constant.InitialResources;
import com.court_booking_project.court_booking_server.constant.PredefineRole;
import com.court_booking_project.court_booking_server.dto.request.user.UpdateUserRequest;
import com.court_booking_project.court_booking_server.dto.response.CloudinaryResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    MediaService mediaService;

    @Override
    public UserResponse add(CreateUserRequest createUserRequest) {
        var checkEmail = userRepository.findByEmail(createUserRequest.getEmail());

        if(checkEmail.isPresent())
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(createUserRequest);
        user.setCreatedAt(Date.from(java.time.Instant.now()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage(InitialResources.getRandomUrl());
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
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();

        String email = context.getAuthentication().getName();

        var user = userRepository.findByEmail(email);

        if(user.isEmpty())
            throw new AppException(ErrorCode.UNAUTHENTICATED);

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
    public UserResponse update(String id, UpdateUserRequest updateUserRequest) {
        var userEntity = userRepository.findById(id);

        if (userEntity.isEmpty())
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        userMapper.updateUser(userEntity.get(), updateUserRequest);

        userRepository.save(userEntity.get());
        return userMapper.toUserResponse(userEntity.get());
    }

    @Override
    public UserResponse updateProfilePicture(String id, MultipartFile imageFile) {
        var user = userRepository.findById(id);

        if(user.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXISTED);

        CloudinaryResponse cloudinaryResponse = mediaService.uploadMedia(imageFile, CloudinaryFolder.profile);

        if(cloudinaryResponse == null) throw new AppException(ErrorCode.UPLOAD_FILE_FAILED);

        user.get().setProfileImage(cloudinaryResponse.getUrl());

        userRepository.save(user.get());

        return userMapper.toUserResponse(user.get());
    }

}
