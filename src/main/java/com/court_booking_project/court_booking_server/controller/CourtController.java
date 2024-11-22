package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.service.implementations.CourtServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/courts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourtController {

    CourtServiceImpl courtService;

    @GetMapping
    public List<CourtResponse> getAllCourts() { return  courtService.getAll(); }

    @GetMapping("/type/{typeId}")
        public Page<CourtResponse> getCourtsByType(@PathVariable String typeId, Pageable pageable) {
        return courtService.getCourtsByType(typeId, pageable);
    }

    @GetMapping("/{id}")
    public CourtResponse getCourt(@PathVariable String id) {
        return courtService.get(id);
    }

    @GetMapping("/{id}/get-available-date")
    public List<String> getAvailableDate(@PathVariable String id) {
        return courtService.getAvailableDate(id);
    }

    @GetMapping("/{id}/get-unavailable-hours")
    public List<String> getUnavailableHours(@PathVariable String id,@RequestParam(name = "date") String date) {
        return courtService.getUnavailableHours(id,date);
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
