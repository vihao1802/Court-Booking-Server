package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.dto.response.reservation.ReservationResponse;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByUserOrderByCreatedAt(User user);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (:search IS NULL OR LOWER(r.user.userName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "       OR LOWER(r.user.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "       OR LOWER(r.user.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:fromDate IS NULL OR r.reservationDate >= :fromDate) " +
            "AND (:toDate IS NULL OR r.reservationDate <= :toDate)")
    Page<Reservation> findFilteredReservations(
            @Param("search") String search,
            @Param("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @Param("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            Pageable pageable
    );

    @Modifying
    @Transactional
    @Query("UPDATE Reservation r " +
            "SET r.reservationState = 2 " +
            "WHERE r.reservationState = 0 " +
            "AND CONCAT(r.reservationDate, ' ', CONCAT(r.checkOutTime, ':00:00')) >= :currentTime")
    void updateExpiredReservations(@Param("currentTime") String currentTime);
}
