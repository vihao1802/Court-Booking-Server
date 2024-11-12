package com.court_booking_project.court_booking_server;

import com.court_booking_project.court_booking_server.config.ZaloPayConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(ZaloPayConfig.class)
public class CourtBookingServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CourtBookingServerApplication.class, args);
	}
	@PostConstruct
	public void printSwaggerUrl() {
		log.info("Swagger URL: http://localhost:8080/api/swagger-ui.html");
	}
}
