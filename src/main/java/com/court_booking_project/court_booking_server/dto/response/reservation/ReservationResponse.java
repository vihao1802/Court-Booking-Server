package com.court_booking_project.court_booking_server.dto.response.reservation;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationResponse {
    String id;
    String userId;
    String courtId;
    String checkInTime;
    String checkOutTime;
    long totalPrice;
    String reservationDate;
    ReservationState reservationState;
    String paymentMethod;

    public int getReservationState() {
        return reservationState != null ? reservationState.ordinal() : -1; // return -1 if null or choose a default value
    }
}
