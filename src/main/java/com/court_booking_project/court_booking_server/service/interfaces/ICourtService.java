package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CreateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourtService {
    Page<CourtResponse> getCourtsByType(String typeId,Pageable pageable);
    List<CourtResponse> getAll();
    CourtResponse get(String id);
    CourtResponse add(CreateCourtRequest request);
    CourtResponse update(String id, UpdateCourtRequest request);
    CourtResponse creatCourtImageList(String id, CreateCourtImageRequest requests);
}
