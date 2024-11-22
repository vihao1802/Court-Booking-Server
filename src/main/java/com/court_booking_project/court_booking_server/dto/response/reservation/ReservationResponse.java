package com.court_booking_project.court_booking_server.dto.response.reservation;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import com.court_booking_project.court_booking_server.dto.response.authentication.UserResponse;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationResponse {
    String id;
    String checkInTime;
    String checkOutTime;
    long totalPrice;
    String reservationDate;
    ReservationState reservationState;
    String paymentMethod;
    CourtResponse court;
    UserResponse user;
    LocalDateTime createdAt;

    public int getReservationState() {
        return reservationState != null ? reservationState.ordinal() : -1; // return -1 if null or choose a default value
    }
}
