package com.court_booking_project.court_booking_server.service.interfaces;

import com.cloudinary.Cloudinary;
import com.court_booking_project.court_booking_server.constant.CloudinaryFolder;
import com.court_booking_project.court_booking_server.dto.response.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IMediaService {
    CloudinaryResponse uploadMedia(final MultipartFile multipartFile, final CloudinaryFolder folder);
    void deleteMedia(String public_id);
}
