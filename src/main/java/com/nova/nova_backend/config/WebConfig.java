package com.nova.nova_backend.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public Filter originLoggingFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                String originHeader = httpRequest.getHeader("Origin");

                System.out.println("Incoming request Origin: " + originHeader);
                System.out.println("Request Method: " + httpRequest.getMethod());

                // rs-nova.co.kr이 포함된 Origin을 모두 허용
                if (originHeader != null && originHeader.contains("rs-nova.co.kr")) {
                    httpResponse.setHeader("Access-Control-Allow-Origin", originHeader);
                    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
                    httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
                    httpResponse.setHeader("Access-Control-Allow-Headers", "*");
                }

                chain.doFilter(request, response);
            }
        };
    }
}
