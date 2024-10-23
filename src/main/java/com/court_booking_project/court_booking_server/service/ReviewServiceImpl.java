package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.Review;
import com.court_booking_project.court_booking_server.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void add(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Review get(String id) {
        return reviewRepository.findById(id).get();
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }
}
