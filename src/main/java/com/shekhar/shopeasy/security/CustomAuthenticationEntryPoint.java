package com.shekhar.shopeasy.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shekhar.shopeasy.dto.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        response.setContentType("application/json");

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED
        );

        ApiResponse apiResponse =
                new ApiResponse(
                        false,
                        "Unauthorized Access",
                        LocalDateTime.now()
                );

        response.getWriter()
                .write(
                        objectMapper.writeValueAsString(apiResponse)
                );
    }
}