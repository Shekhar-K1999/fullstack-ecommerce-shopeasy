package com.shekhar.shopeasy.exception;

public class InvalidPhoneException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPhoneException(String message) {
		super(message);
	}
}