package com.example.blog.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://forumfr.s3-website.ap-northeast-2.amazonaws.com","https://d3v7vlgblk96a0.cloudfront.net/login")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}