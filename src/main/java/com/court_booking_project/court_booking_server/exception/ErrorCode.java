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
    EMPTY_COURT_NAME(1009, "courtName cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_COURT_DESCRIPTION(1010, "courtDescription cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_ADDRESS(1011, "courtAddress cannot be empty", HttpStatus.BAD_REQUEST),
    REQUIRED_RENTAL_PRICE_PER_HOUR(1012, "rentalPricePerHour cannot be empty", HttpStatus.BAD_REQUEST),
    INVALID_RENTAL_PRICE_PER_HOUR(1013, "rentalPricePerHour must be greater than 0", HttpStatus.BAD_REQUEST),
    REQUIRED_MINIMUM_RENTAL_TIME(1014, "minimumRentalTime cannot be empty", HttpStatus.BAD_REQUEST),
    INVALID_MINIMUM_RENTAL_TIME(1015, "minimumRentalTime must be greater than 0", HttpStatus.BAD_REQUEST),
    REQUIRED_MAXIMUM_RENTAL_TIME(1016, "maximumRentalTime cannot be empty", HttpStatus.BAD_REQUEST),
    INVALID_MAXIMUM_RENTAL_TIME(1017, "maximumRentalTime must be greater than 0", HttpStatus.BAD_REQUEST),
    INVALID_COURT_TYPE_ID(1018, "courtTypeId cannot be null", HttpStatus.BAD_REQUEST),
    EMPTY_COURT_TYPE_ID(1019, "courtTypeId cannot be empty", HttpStatus.BAD_REQUEST),
    NOT_FOUND_COURT_TYPE_ID(1020, "courtTypeId not found", HttpStatus.NOT_FOUND),
    EMPTY_CHECK_IN_TIME(1021, "checkInTime cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_CHECK_OUT_TIME(1022, "checkOutTime cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_TOTAL_PRICE(1023, "totalPrice cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_RESERVATION_DATE(1024, "reservationDate cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_RESERVATION_STATE(1025, "reservationState cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_USER_ID(1026, "userId cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_COURT_ID(1027, "courtId cannot be empty", HttpStatus.BAD_REQUEST),
    NOT_FOUND_COURT_ID(1028, "courtId not found", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER_ID(1029, "userId not found", HttpStatus.NOT_FOUND),
    NOT_FOUND_RESERVATION_ID(1030,"reservationId not found", HttpStatus.NOT_FOUND),
    INVALID_PAYMENT_METHOD(1031,"Invalid paymentMethod", HttpStatus.BAD_REQUEST),
    INVALID_RESERVATION_STATE(1032, "Invalid reservation state", HttpStatus.BAD_REQUEST);
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
