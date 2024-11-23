package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.response.ApiResponse;
import com.court_booking_project.court_booking_server.dto.response.statistic.RevenueByMonthResponse;
import com.court_booking_project.court_booking_server.service.implementations.UserServiceImpl;
import com.court_booking_project.court_booking_server.service.interfaces.IReservationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RequestMapping("/api/v1/statistic")
public class StatisticController {
    IReservationService reservationService;
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/total-booking-hours")
    public ResponseEntity<ApiResponse<?>> getTotalBookingHours(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Integer totalBookingHours = reservationService.getTotalBookingHours(startDate, endDate);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .result(totalBookingHours == null ? 0 : totalBookingHours)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/total-profit")
    public ResponseEntity<ApiResponse<?>> getTotalProfit(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Integer totalProfit = reservationService.getTotalProfit(startDate, endDate);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .result(totalProfit == null ? 0 : totalProfit)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/total-new-user")
    public ResponseEntity<?> getNewUser(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Integer totalNewUser = userServiceImpl.getTotalNewUser(startDate, endDate);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .result(totalNewUser == null ? 0 : totalNewUser)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/revenue-by-month")
    public ResponseEntity<ApiResponse<?>> getRevenueByMonth(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<RevenueByMonthResponse> revenueByMonthResponse  = reservationService.getRevenueByMonths(startDate, endDate);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .result(revenueByMonthResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
