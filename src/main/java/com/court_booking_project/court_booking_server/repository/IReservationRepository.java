package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.dto.response.statistic.RevenueByMonthResponse;
import com.court_booking_project.court_booking_server.constant.ReservationState;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByUserOrderByCreatedAt(User user);
    List<Reservation> findByReservationDateBetween(LocalDate start, LocalDate end);
    List<Reservation> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = "SELECT SUM(TIMESTAMPDIFF(HOUR, r.check_in_time, r.check_out_time)) " +
            "FROM reservations r " +
            "WHERE r.reservation_state = 1 AND r.created_at BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Integer getTotalBookingHours(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT SUM(r.total_price)" +
            "FROM reservations r " +
            "WHERE r.reservation_state = 1 AND r.created_at BETWEEN :startDate AND :endDate",
    nativeQuery = true)
    Integer getTotalProfit(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT " +
            "MONTH(r.created_at) AS month, " +
            "YEAR(r.created_at) AS year, " +
            "SUM(r.total_price) AS revenue, " +
            "SUM(TIMESTAMPDIFF(HOUR, r.check_in_time, r.check_out_time)) AS totalHours " +
            "FROM reservations r " +
            "WHERE r.reservation_state = 1 AND r.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(r.created_at) , MONTH(r.created_at)" +
            "ORDER BY year ASC, month ASC;", nativeQuery = true)
    List<Object[]> getRevenueByMonths(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT r FROM Reservation r WHERE r.reservationDate = :date AND r.court.id = :courtId AND r.reservationState IN :states")
    List<Reservation> findByReservationDateAndCourtIdAndReservationStates(
            @Param("date") LocalDate date,
            @Param("courtId") String courtId,
            @Param("states") List<ReservationState> states
    );

    @Query("SELECT r FROM Reservation r " +
            "WHERE (:search IS NULL OR LOWER(r.user.userName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "       OR LOWER(r.user.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "       OR LOWER(r.user.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:fromDate IS NULL OR r.reservationDate >= :fromDate) " +
            "AND (:toDate IS NULL OR r.reservationDate <= :toDate)")
    Page<Reservation> findFilteredReservations(
            @Param("search") String search,
            @Param("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @Param("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            Pageable pageable
    );


    @Modifying
    @Transactional
    @Query("UPDATE Reservation r " +
            "SET r.reservationState = 2 " +
            "WHERE r.reservationState = 0 " +
            "AND CONCAT(r.reservationDate, ' ', CONCAT(r.checkOutTime, ':00:00')) < :currentTime")
    void updateExpiredReservations(@Param("currentTime") String currentTime);

}
