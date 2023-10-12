package com.example.springclient.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Allow requests from specific origins
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200", "studio.apollographql.com", "http://localhost"));

        // Allow specific HTTP methods
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD"));

        // Allow specific HTTP headers
        corsConfig.setAllowedHeaders(List.of(
                "Origin", "Accept", "Content-Type", "Authorization",
                "Allow", "Content-Length", "Date",
                "If-Match", "If-Not-Match",
                "sec-fetch-mode", "sec-fetch-site", "sec-fetch-dest"
        ));

        // Expose specific response headers
        corsConfig.setExposedHeaders(List.of(
                "Content-Type", "Content-Length", "ETag", "Location", "Date",
                "Last-Modified", "Access-Control-Allow-Origin", "Content-Security-Policy",
                "Strict-Transport-Security", "X-Content-Type-Options"
        ));

        // Set the maximum age of the preflight request in seconds
        corsConfig.setMaxAge(Duration.ofDays(86400));

        // Create a URL-based CORS configuration source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }
}