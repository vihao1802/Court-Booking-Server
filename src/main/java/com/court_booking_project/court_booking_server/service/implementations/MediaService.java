package com.court_booking_project.court_booking_server.service.implementations;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.court_booking_project.court_booking_server.constant.CloudinaryFolder;
import com.court_booking_project.court_booking_server.dto.response.CloudinaryResponse;
import com.court_booking_project.court_booking_server.exception.AppException;
import com.court_booking_project.court_booking_server.exception.ErrorCode;
import com.court_booking_project.court_booking_server.service.interfaces.IMediaService;
import com.court_booking_project.court_booking_server.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService implements IMediaService {

    private final Cloudinary cloudinary;

    @Override
    public CloudinaryResponse uploadMedia(final MultipartFile multipartFile, final CloudinaryFolder folder) {
        if (multipartFile.isEmpty()) {
            throw new AppException(ErrorCode.FILE_IS_EMPTY);
        }
        FileUploadUtil.assertAllowed(multipartFile, FileUploadUtil.IMAGE_PATTERN);

        try {
            final String fileName = FileUploadUtil.getFileName( multipartFile.getOriginalFilename());

            //upload
            final Map result = this.cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("public_id",fileName,"folder",folder.toString()));

            //get url and public id
            final String url = (String) result.get("url");
            final String publicId = (String) result.get("public_id");

            return new CloudinaryResponse(publicId, url);
        } catch (Exception e) {
            log.error(e.toString());
            throw new AppException(ErrorCode.UPLOAD_FILE_FAILED);
        }
    }
    @Override
    public void deleteMedia(String public_id) {
        try {
            this.cloudinary.uploader().destroy(public_id, Map.of());
        } catch (Exception e) {
            throw new AppException(ErrorCode.DELETE_FILE_FAILED);
        }
    }


}
