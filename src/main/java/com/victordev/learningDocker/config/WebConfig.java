package com.victordev.learningDocker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")     // any mapping will access to the routes
                .allowedOrigins("https://todolist-app-production.up.railway.app")
                .allowedMethods("POST", "GET", "DELETE", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
