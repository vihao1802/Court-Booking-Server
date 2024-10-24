package com.court_booking_project.court_booking_server.service.Interfaces;

import com.court_booking_project.court_booking_server.entity.CourtType;

import java.util.List;

public interface ICourtTypeService {
    List<CourtType> getAll();
    CourtType add(CourtType courtType);
}