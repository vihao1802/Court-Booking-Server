package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.dto.response.statistic.RevenueByMonthResponse;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByUserOrderByCreatedAt(User user);

    @Query("SELECT SUM(TIMESTAMPDIFF(HOUR, r.checkInTime, r.checkOutTime))  FROM Reservation r WHERE r.reservationState = 1 AND r.createdAt BETWEEN :startDate AND :endDate")
    Integer getTotalBookingHours(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT SUM(r.totalPrice) FROM Reservation r WHERE r.reservationState = 1 AND r.createdAt BETWEEN :startDate AND :endDate")
    Integer getTotalProfit(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT " +
            "MONTH(r.created_at) AS month, " +
            "YEAR(r.created_at) AS year, " +
            "SUM(r.total_price) AS revenue, " +
            "SUM(TIMESTAMPDIFF(HOUR, r.check_in_time, r.check_out_time)) AS totalHours " +
            "FROM reservations r " +
            "WHERE r.reservation_state = 1 AND r.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(r.created_at), MONTH(r.created_at)", nativeQuery = true)
    List<Object[]> getRevenueByMonths(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
