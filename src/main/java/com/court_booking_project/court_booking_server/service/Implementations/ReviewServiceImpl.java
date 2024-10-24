package com.court_booking_project.court_booking_server.service.Implementations;

import com.court_booking_project.court_booking_server.entity.Review;
import com.court_booking_project.court_booking_server.repository.IReviewRepository;
import com.court_booking_project.court_booking_server.service.Interfaces.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    private final IReviewRepository reviewRepository;

    public ReviewServiceImpl(IReviewRepository reviewRepository) {
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
