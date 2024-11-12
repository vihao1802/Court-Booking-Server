package com.court_booking_project.court_booking_server.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CloudinaryResponse {
    private String public_id;

    private String url;
}
