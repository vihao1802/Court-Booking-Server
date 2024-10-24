package com.court_booking_project.court_booking_server.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
