package com.court_booking_project.court_booking_server.repository;

import com.court_booking_project.court_booking_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

}
