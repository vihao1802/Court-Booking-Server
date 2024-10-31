package com.court_booking_project.court_booking_server.dto.request.authentication;

import com.court_booking_project.court_booking_server.custom_annotation.PasswordConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequestDto {
    @Schema(description = "Email", example = "admin@admin.com")
    String email;

    @Schema(description = "Password", example = "admin")
    String password;
}
