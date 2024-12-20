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
public enum ReservationState {
    PENDING(0),
    SUCCESS(1),
    FAILED(2);

    int code;

    public static ReservationState fromCode(int code) {
        for (ReservationState state : ReservationState.values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        throw new AppException(ErrorCode.INVALID_RESERVATION_STATE);
    }
}

