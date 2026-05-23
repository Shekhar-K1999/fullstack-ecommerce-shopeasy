package com.shekhar.shopeasy.exception;

import com.shekhar.shopeasy.dto.response.ApiResponse;
import com.shekhar.shopeasy.dto.response.ValidationErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// =========================
	// EMAIL EXISTS
	// =========================

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse> handleEmailExists(EmailAlreadyExistsException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, ex.getMessage(), null));
	}

	// =========================
	// PHONE EXISTS
	// =========================

	@ExceptionHandler(PhoneAlreadyExistsException.class)
	public ResponseEntity<ApiResponse> handlePhoneExists(PhoneAlreadyExistsException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, ex.getMessage(), null));
	}

	// =========================
	// PASSWORD MISMATCH
	// =========================

	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ApiResponse> handlePasswordMismatch(PasswordMismatchException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
	}

	// =========================
	// WEAK PASSWORD
	// =========================

	@ExceptionHandler(WeakPasswordException.class)
	public ResponseEntity<ApiResponse> handleWeakPassword(WeakPasswordException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
	}

	// =========================
	// INVALID PHONE
	// =========================

	@ExceptionHandler(InvalidPhoneException.class)
	public ResponseEntity<ApiResponse> handleInvalidPhone(InvalidPhoneException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
	}

	// =========================
	// INVALID IMAGE
	// =========================

	@ExceptionHandler(InvalidImageException.class)
	public ResponseEntity<ApiResponse> handleInvalidImage(InvalidImageException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
	}

	// =========================
	// USER REGISTRATION ERROR
	// =========================

	@ExceptionHandler(UserRegistrationException.class)
	public ResponseEntity<ApiResponse> handleRegistrationError(UserRegistrationException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
	}

	// =========================
	// @VALID VALIDATION
	// =========================

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {

			String fieldName = ((FieldError) error).getField();

			String message = error.getDefaultMessage();

			errors.put(fieldName, message);
		});

		ValidationErrorResponse response = new ValidationErrorResponse(false, "Validation Failed",
				HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors);

		return ResponseEntity.badRequest().body(response);
	}

	// =========================
	// ALL OTHER ERRORS
	// =========================

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {

		ex.printStackTrace();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponse(false, "Something went wrong", null));
	}
}