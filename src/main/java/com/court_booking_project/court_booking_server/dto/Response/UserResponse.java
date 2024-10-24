package com.court_booking_project.court_booking_server.dto.Response;

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
    Date dayOfBirth;
    Date createdAt;
    String location;
}
