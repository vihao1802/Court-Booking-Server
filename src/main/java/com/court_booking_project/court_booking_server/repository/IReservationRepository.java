package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.constant.ReservationState;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByUserOrderByCreatedAt(User user);
    List<Reservation> findByReservationDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT r FROM Reservation r WHERE r.reservationDate = :date AND r.court.id = :courtId AND r.reservationState IN :states")
    List<Reservation> findByReservationDateAndCourtIdAndReservationStates(
            @Param("date") LocalDate date,
            @Param("courtId") String courtId,
            @Param("states") List<ReservationState> states
    );
}
