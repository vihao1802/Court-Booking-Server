package com.court_booking_project.court_booking_server.dto.request.court_image;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty(message = "courtImageSrc cannot be an empty string")
    String courtImageSrc;

    @NotEmpty(message = "imageType cannot be an empty string")
    String imageType;
}
