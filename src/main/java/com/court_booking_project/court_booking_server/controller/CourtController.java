package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.service.implementations.CourtServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/courts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourtController {

    CourtServiceImpl courtService;

    @GetMapping
    public List<CourtResponse> getAllCourts() {
        return courtService.getAll();
    }

    @GetMapping("/{id}")
    public CourtResponse getCourt(@PathVariable String id) {
        return courtService.get(id);
    }

    @PostMapping
    public CourtResponse addCourt(@RequestBody @Valid CreateCourtRequest request) {
        return courtService.add(request);
    }

    @PutMapping("/{id}")
    public CourtResponse updateCourt(@PathVariable String id,@RequestBody @Valid UpdateCourtRequest request) {
        return courtService.update(id, request);
    }

}
