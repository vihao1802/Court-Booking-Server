package com.court_booking_project.court_booking_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                registry.addMapping("/**") // Ánh xạ tất cả các URL
                        .allowedMethods("*") // Cho phép tất cả HTTP methods
                        .allowedOrigins("*") // Cho phép mọi nguồn gốc
                        .allowedHeaders("*"); // Cho phép mọi loại header

            }
        };
    }
}
