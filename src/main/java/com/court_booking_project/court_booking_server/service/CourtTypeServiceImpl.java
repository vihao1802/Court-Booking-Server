package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.Court;
import com.court_booking_project.court_booking_server.repository.CourtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtTypeServiceImpl implements CourtService{

    private final CourtRepository courtRepository;

    public CourtTypeServiceImpl(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    @Override
    public Court get(String id) {
        return courtRepository.findById(id).get();
    }

    @Override
    public List<Court> getAll() {
        return courtRepository.findAll();
    }
}