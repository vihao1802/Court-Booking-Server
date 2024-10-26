package com.court_booking_project.court_booking_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1004, "Tên người dùng không được trống", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1004, "Ngày sinh không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(1004, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1004, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    FIELD_EMPTY(1004, "Các trường thông tin không dược trống", HttpStatus.BAD_REQUEST),
    FIELD_NULL(1004, "Các trường thông tin không dược null", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_TOKEN(1008, "Invalid token", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }


    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
