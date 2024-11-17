package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByUserOrderByCreatedAt(User user);
}
