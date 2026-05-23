package com.shekhar.shopeasy.validation;

public class ValidationMessages {

	private ValidationMessages() {
	}

	public static final String INVALID_EMAIL = "Please enter a valid email address";

	public static final String INVALID_PHONE = "Please enter valid Indian mobile number";

	public static final String WEAK_PASSWORD = "Password must contain uppercase, lowercase, number and special character";

	public static final String PASSWORD_NOT_MATCH = "Passwords do not match";

	public static final String EMAIL_EXISTS = "Email already registered";

	public static final String PHONE_EXISTS = "Phone number already registered";

	public static final String INVALID_IMAGE = "Only image files allowed";

	public static final String FILE_TOO_LARGE = "Image size must be less than 2MB";
}