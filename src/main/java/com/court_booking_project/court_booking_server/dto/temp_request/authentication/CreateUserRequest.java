package com.court_booking_project.court_booking_server.dto.temp_request.authentication;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotNull(message="Tên không được để trống")
    private String userName;

    @NotNull(message="Email không được để trống")
    @Email(message="Email không hợp lệ ")
    private String email;

    @NotNull(message="Mật khẩu không được để trống")
    @Size(min=8, message="Password must be 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Mật khẩu phải có ít nhất một từ ký tự hoa, một ký tự thường và một ký tự đặc biệt"
    )
    private String password;

    @NotNull(message="Số điện thoại không được để trống")
    @Size(min=10, max=11, message="Số điện thoại phải có 10 hoặc 11 số")
    private String phoneNumber;

    @NotNull(message="Ngày sinh không được để trống")
    @Past
    private Date dayOfBirth;
}
