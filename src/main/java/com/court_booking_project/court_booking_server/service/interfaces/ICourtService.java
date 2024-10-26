package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.entity.Court;

import java.util.List;

public interface ICourtService {
    List<CourtResponse> getAll();
    CourtResponse get(String id);
    CourtResponse add(CreateCourtRequest request);
    CourtResponse update(String id, UpdateCourtRequest request);
}
