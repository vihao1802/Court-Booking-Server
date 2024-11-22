package com.court_booking_project.court_booking_server.dto.request.court_image;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourtImageFileRequest {
    @NotNull(message = "courtImageSrc cannot be null")
    MultipartFile courtImageSrc;

    @NotEmpty(message = "EMPTY_IMAGE_TYPE")
    String imageType;
}
