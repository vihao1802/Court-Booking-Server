package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
        SELECT u 
        FROM User u 
        WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) 
           OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) 
           OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<User> getUsers(@Param("keyword") String keyword, Pageable pageable);

}
