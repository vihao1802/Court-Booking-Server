package com.court_booking_project.court_booking_server;

import com.court_booking_project.court_booking_server.config.ZaloPayConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ZaloPayConfig.class)
public class CourtBookingServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CourtBookingServerApplication.class, args);
	}

}
