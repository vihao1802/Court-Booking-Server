package com.court_booking_project.court_booking_server.dto.temp_request.court_image;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCourtImageRequest {
    String courtImageSrc;
    String imageType;
    String courtId;
}
