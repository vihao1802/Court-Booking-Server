package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u.id) FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    Integer getTotalNewUser(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
