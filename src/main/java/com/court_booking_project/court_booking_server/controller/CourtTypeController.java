package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.court_type.CreateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.request.court_type.UpdateCourtTypeRequest;
import com.court_booking_project.court_booking_server.dto.response.courtType.CourtTypeResponse;
import com.court_booking_project.court_booking_server.service.implementations.CourtTypeServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/court-types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourtTypeController {
    CourtTypeServiceImpl courtTypeService;

    @GetMapping
    public List<CourtTypeResponse> getAllCourtTypes() {
        return courtTypeService.getAll();
    }

    @GetMapping("/{id}")
    public CourtTypeResponse getCourtTypeById(@PathVariable String id) {
        return courtTypeService.get(id);
    }

    @PostMapping
    public CourtTypeResponse createCourtType(@RequestBody CreateCourtTypeRequest request) {
        return courtTypeService.add(request);
    }

    @PutMapping("/{id}")
    public CourtTypeResponse updateCourtType(@PathVariable String id, @RequestBody UpdateCourtTypeRequest request) {
        return courtTypeService.update(id, request);
    }

}
