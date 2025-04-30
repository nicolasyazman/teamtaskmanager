package io.github.nicolasyazman.teamtaskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true); // Allow cookies (optional, depending on your auth system)
        config.setAllowedOrigins(List.of(
        	    "http://localhost:5173",
        	    "http://localhost:3000"
        	)); // <-- Frontend URL (adjust if different)
        config.setAllowedHeaders(List.of("*")); // Allow all headers (Authorization, Content-Type, etc.)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow HTTP methods

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS to all endpoints
        
        return new CorsFilter(source);
    }
}