package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.CourtType;

import java.util.List;

public interface CourtTypeService {
    List<CourtType> getAll();
    CourtType add(CourtType courtType);
}