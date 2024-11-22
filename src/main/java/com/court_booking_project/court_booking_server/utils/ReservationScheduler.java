package com.court_booking_project.court_booking_server.utils;

import com.court_booking_project.court_booking_server.repository.IReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@EnableScheduling
public class ReservationScheduler {

    @Autowired
    private IReservationRepository reservationRepository;

    @Scheduled(fixedRate = 60000) // Chạy mỗi 1 phút
    public void updateReservationsState() {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("Current time: " + currentTime);
        reservationRepository.updateExpiredReservations(currentTime);
    }
}
