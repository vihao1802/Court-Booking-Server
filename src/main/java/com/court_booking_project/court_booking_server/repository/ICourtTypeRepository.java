package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.dto.response.courtType.CourtTypeResponse;
import com.court_booking_project.court_booking_server.entity.CourtType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourtTypeRepository extends JpaRepository<CourtType,String> {
    List<CourtType> findByIsDisabled(int isDisabled);
}