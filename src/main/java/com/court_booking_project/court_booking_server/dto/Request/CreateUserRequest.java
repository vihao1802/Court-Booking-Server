package com.court_booking_project.court_booking_server.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    private String userName;

    @Email(message="Email không hợp lệ ")
    private String email;

    @Size(min=8, message="Password must be 8 characters long")
//    @Pattern(
//            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*/d)(?=.*[@$!%*?&])[A-Za-z/d@$!%*?&]{6,}$",
//            message = "Mật khẩu phải có ít nhất một từ ký tự hoa, một ký tự thường và một ký tự đặc biệt"
//    )
    private String password;

    @Size(min=10, max=11, message="Số điện thoại phải có 10 hoặc 11 số")
    private String phoneNumber;
}
