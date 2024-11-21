package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.dto.request.court.CreateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court.UpdateCourtRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.CreateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.request.court_image.UpdateCourtImageRequest;
import com.court_booking_project.court_booking_server.dto.response.court.CourtResponse;
import com.court_booking_project.court_booking_server.service.interfaces.ICourtService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/courts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourtController {

    ICourtService courtService;
    private static final Log log = LogFactory.getLog(CourtController.class);

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

    @PostMapping
    public CourtResponse addCourt(@RequestBody @Valid CreateCourtRequest request) {

        return courtService.add(request);
    }

    @PostMapping("/create-image-list")
    public CourtResponse createCourtImageList(@RequestParam("court-id") String courtId, @ModelAttribute @Valid CreateCourtImageRequest requests) {
        log.info(requests);
        return courtService.creatCourtImageList(courtId, requests);
    }

    @PutMapping("/{id}")
    public CourtResponse updateCourt(@PathVariable String id,@RequestBody @Valid UpdateCourtRequest request) {
        return courtService.update(id, request);
    }
}
