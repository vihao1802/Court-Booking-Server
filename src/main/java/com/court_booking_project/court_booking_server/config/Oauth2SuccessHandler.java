package com.court_booking_project.court_booking_server.config;

import com.court_booking_project.court_booking_server.constant.PredefineRole;
import com.court_booking_project.court_booking_server.dto.request.authentication.CreateUserRequest;
import com.court_booking_project.court_booking_server.dto.request.authentication.LoginRequestDto;
import com.court_booking_project.court_booking_server.dto.response.authentication.AuthenticationResponse;
import com.court_booking_project.court_booking_server.entity.User;
import com.court_booking_project.court_booking_server.mapper.UserMapper;
import com.court_booking_project.court_booking_server.repository.IUserRepository;
import com.court_booking_project.court_booking_server.service.interfaces.IAuthenticationService;
import com.court_booking_project.court_booking_server.service.interfaces.IRoleService;
import com.court_booking_project.court_booking_server.service.interfaces.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    IAuthenticationService authenticationService;
    IUserRepository userRepository;
    IRoleService roleService;

    @NonFinal
    @Value("${frontend.url}")
    String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String profileImage = oAuth2User.getAttribute("picture");

        var user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            String passwordRandom = UUID.randomUUID().toString();
            User newUser = User.builder()
                    .userName(name)
                    .email(email)
                    .profileImage(profileImage)
                    .password(passwordRandom)
                    .phoneNumber("0987654321")
                    .dayOfBirth(java.time.LocalDate.now())
                    .role(roleService.getByName(PredefineRole.USER_ROLE))
                    .createdAt(java.util.Date.from(java.time.Instant.now()))
                    .isChangedPassword(false)
                    .isDisabled(0)
                    .build();
            userRepository.save(newUser);
        }
        var userJustCreated  = userRepository.findByEmail(email);
        String token= authenticationService.generateToken(userJustCreated.get());
        logger.warn("Token: "+token);
        String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl)
                .queryParam("token", token)
                .build()
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
