package com.court_booking_project.court_booking_server.service.interfaces;

import com.court_booking_project.court_booking_server.dto.request.court_image.CourtImageFileRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.courtImage.CourtImageResponse;

import java.util.List;

public interface ICourtImageService {
    List<CourtImageResponse> getAll();
    CourtImageResponse get(String courtId);
    CourtImageResponse update(String id,CourtImageFileRequest request);
}
