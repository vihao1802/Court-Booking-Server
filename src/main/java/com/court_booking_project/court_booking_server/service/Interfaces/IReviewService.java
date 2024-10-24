package com.court_booking_project.court_booking_server.service.Interfaces;

import com.court_booking_project.court_booking_server.entity.Review;

import java.util.List;


public interface IReviewService {
    void add(Review review);
    Review get(String id);
    List<Review> getAll();
}
