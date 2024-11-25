package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.entity.Court;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ICourtRepository extends JpaRepository<Court,String> {
    Page<Court> findByCourtTypeIdAndIsDeletedNot(String id, int isDelete, Pageable pageable);

    @Query("SELECT c " +
            "FROM Court c " +
            "WHERE c.courtType.id = :typeId " +
            "AND c.isDeleted = 0 " +
            "AND NOT EXISTS (" +
            "  SELECT 1 FROM Reservation r " +
            "  WHERE r.court.id = c.id " +
            "  AND r.reservationDate = :date " +
            "  AND SQL('CAST(? AS INT)', r.checkInTime) < :end " +
            "  AND SQL('CAST(? AS INT)', r.checkOutTime) > :start " +
            "  AND r.reservationState != 2" +
            ")")
    Page<Court> getAvailableCourtsByTypeAndDateTime(
            @Param("typeId") String typeId,
            @Param("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @Param("start") int start,
            @Param("end") int end,
            Pageable pageable);
}
