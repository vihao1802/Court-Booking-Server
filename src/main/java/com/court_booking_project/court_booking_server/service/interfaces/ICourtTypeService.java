package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.court_type.CreateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.request.court_type.UpdateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.response.courtType.CourtTypeResponse;

import java.util.List;

public interface ICourtTypeService {
    List<CourtTypeResponse> getAll();
    CourtTypeResponse get(String id);
    CourtTypeResponse add(CreateCourtTypeRequest request);
    CourtTypeResponse update(String id, UpdateCourtTypeRequest request);
}