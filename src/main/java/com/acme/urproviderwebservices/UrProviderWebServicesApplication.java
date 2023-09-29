package com.acme.urproviderwebservices;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.net.URL;
import java.util.Arrays;

@OpenAPIDefinition(info=@Info(version = "V1",
        title="UrProvider API",
        description="UrProvider RESTful API",
        termsOfService="https://UrProvider.github.io/Landing-Page-UrProvide/"

))
@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class UrProviderWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrProviderWebServicesApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://hesitant-turn-production.up.railway.app/")
                        .allowedMethods("GET", "POST", "PUT")
                        .allowedHeaders("Access-Control-Allow-Origin", "Content-Type", "Authorization");
            }
        };
    }
}
