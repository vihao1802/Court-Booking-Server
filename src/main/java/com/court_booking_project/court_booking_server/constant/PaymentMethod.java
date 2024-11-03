package com.court_booking_project.court_booking_server.constant;

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
    CASH
}
