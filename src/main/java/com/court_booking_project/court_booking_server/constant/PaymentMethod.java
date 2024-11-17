package com.court_booking_project.court_booking_server.constant;

import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum PaymentMethod {
    ZALOPAY,
    MOMO,
    CASH,
    NONE;

    public static PaymentMethod fromString(String method) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equalsIgnoreCase(method)) {
                return paymentMethod;
            }
        }
        throw new AppException(ErrorCode.INVALID_PAYMENT_METHOD);
    }
}
