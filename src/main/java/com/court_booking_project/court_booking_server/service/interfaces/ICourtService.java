package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.entity.Court;

import java.util.List;

public interface ICourtService {
    Court get(String id);
    List<Court> getAll();
}