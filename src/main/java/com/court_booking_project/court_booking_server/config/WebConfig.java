package com.court_booking_project.court_booking_server.config;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${cloudinary.secretKey}")
    String CLOUDINARY_SECRET_KEY;

    @Value("${cloudinary.APIKey}")
    String CLOUDINARY_API_KEY;

    @Value("${cloudinary.cloudName}")
    String CLOUDINARY_CLOUD_NAME;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Register the JavaTimeModule to handle Java 8 date and time types
        objectMapper.registerModule(new JavaTimeModule());
        // Disable the default behavior of writing dates as timestamps (milliseconds since epoch)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Retrieve the existing list of message converters
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        // Check if MappingJackson2HttpMessageConverter is already present
        boolean hasJacksonConverter = messageConverters.stream()
                .anyMatch(converter -> converter instanceof MappingJackson2HttpMessageConverter);

        // If not present, add it
        if (!hasJacksonConverter) {
            messageConverters.add(new MappingJackson2HttpMessageConverter());
        }
        return restTemplate;
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUDINARY_CLOUD_NAME);
        config.put("api_key", CLOUDINARY_API_KEY);
        config.put("api_secret", CLOUDINARY_SECRET_KEY);
        return new Cloudinary(config);
    }
}
