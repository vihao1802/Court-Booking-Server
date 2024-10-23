package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court,String> {
}
