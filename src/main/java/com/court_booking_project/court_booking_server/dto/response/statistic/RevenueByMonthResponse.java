package com.court_booking_project.court_booking_server.dto.response.statistic;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevenueByMonthResponse {
    int month;
    int year;
    BigDecimal revenue;
    BigDecimal bookingHours;
}
