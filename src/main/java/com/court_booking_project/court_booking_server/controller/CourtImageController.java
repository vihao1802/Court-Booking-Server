package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.court_image.CreateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.courtImage.CourtImageResponse;
import com.court_booking_project.court_booking_server.service.implementations.CourtImageServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("${spring.application.api-prefix}/court-images")
public class CourtImageController {

    CourtImageServiceImpl courtImageService;

    @GetMapping
    public List<CourtImageResponse> getAllCourtImages() {
        return courtImageService.getAll();
    }

    @GetMapping("/{id}")
    public CourtImageResponse getCourtImageById(@PathVariable String id) {
        return courtImageService.get(id);
    }

    /*@PostMapping
    public CourtImageResponse createCourtImage(@RequestBody @Valid CreateCourtImageRequest request) {
        return courtImageService.add(request);
    }*/

    @PutMapping("/{id}")
    public CourtImageResponse updateCourtImage(@PathVariable String id, @RequestBody @Valid UpdateCourtImageRequest request) {
        return courtImageService.update(id, request);
    }
}
