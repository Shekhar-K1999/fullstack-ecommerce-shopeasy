package com.shekhar.shopeasy.controller;

import com.shekhar.shopeasy.dto.request.RegisterRequest;
import com.shekhar.shopeasy.dto.response.ApiResponse;
import com.shekhar.shopeasy.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {

		this.authService = authService;
	}

	// =========================
	// REGISTER API
	// =========================

	@PostMapping(value = "/register", consumes = { "multipart/form-data" })
	public ResponseEntity<ApiResponse> register(

			@Valid @ModelAttribute RegisterRequest request) {

		ApiResponse response = authService.register(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}