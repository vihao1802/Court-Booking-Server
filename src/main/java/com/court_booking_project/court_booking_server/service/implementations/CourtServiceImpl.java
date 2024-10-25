package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.entity.Court;
import com.court_booking_project.court_booking_server.repository.ICourtRepository;
import com.court_booking_project.court_booking_server.service.interfaces.ICourtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtServiceImpl implements ICourtService {

    private final ICourtRepository courtRepository;

    public CourtServiceImpl(ICourtRepository courtRepository) {
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
