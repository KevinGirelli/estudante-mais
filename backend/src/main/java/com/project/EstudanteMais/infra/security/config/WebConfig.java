package com.project.EstudanteMais.infra.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://native-randomly-swift.ngrok-free.app") // Substitua pelo seu URL do NGROK
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
