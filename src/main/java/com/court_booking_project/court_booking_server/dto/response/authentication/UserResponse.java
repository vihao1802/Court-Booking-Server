package com.court_booking_project.court_booking_server.dto.response.authentication;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String userName;
    String email;
    String phoneNumber;
    String profileImage;
    Date dayOfBirth;
    Date createdAt;
    boolean gender;
    String location;
}
