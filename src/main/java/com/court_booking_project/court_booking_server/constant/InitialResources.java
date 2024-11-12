package com.court_booking_project.court_booking_server.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@Getter
@AllArgsConstructor
public enum InitialResources {
    PROFILE_SAMPLE_1("https://res.cloudinary.com/dxlnrizu7/image/upload/v1728888785/cld-sample-5.jpg"),
    PROFILE_SAMPLE_2("https://res.cloudinary.com/dxlnrizu7/image/upload/v1728888784/samples/dessert-on-a-plate.jpg"),
    PROFILE_SAMPLE_3("https://res.cloudinary.com/dxlnrizu7/image/upload/v1728888783/samples/breakfast.jpg"),
    PROFILE_SAMPLE_4("https://res.cloudinary.com/dxlnrizu7/image/upload/v1728888782/samples/balloons.jpg"),
    PROFILE_SAMPLE_5("https://res.cloudinary.com/dxlnrizu7/video/upload/v1728888777/samples/sea-turtle.mp4");

    private final String url;


    public static String getRandomUrl() {
        InitialResources[] values = InitialResources.values();
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex].getUrl();
    }
}
