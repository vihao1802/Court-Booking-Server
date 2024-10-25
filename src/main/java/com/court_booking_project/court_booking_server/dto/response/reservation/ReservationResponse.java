package com.court_booking_project.court_booking_server.dto.response.reservation;
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
    Integer totalPrice;
    String reservationDate;
    Integer reservationState;
}
