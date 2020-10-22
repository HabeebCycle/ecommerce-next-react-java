package com.habeebcycle.marketplace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServerConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS;

    public ServerConfig(@Value("${app.config.max_age_secs: 3600}") long max_age_secs) {
        MAX_AGE_SECS = max_age_secs;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "POST", "GET", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }
}
