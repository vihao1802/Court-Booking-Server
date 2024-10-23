package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.reservation.ReservationResponse;
import com.court_booking_project.court_booking_server.service.ReservationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${spring.application.api-prefix}/reservation")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReservationController {
//    ReservationService reservationService;
//
//    @PostMapping("/")
//    ReservationResponse createReservation(@RequestBody ) {
//        return reservationService.add();
//    }
}
