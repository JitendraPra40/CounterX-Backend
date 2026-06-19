package com.restaurant.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173", 
                        "http://localhost:3000",
                        "https://counter-x-frontend-ojqx77wbr-jitendrapra40s-projects.vercel.app",
                        "https://counterx-frontend.vercel.app" // Adding a typical alias just in case
                )
                // Also allow all vercel preview deployments if needed using patterns:
                // .allowedOriginPatterns("https://*.vercel.app", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
