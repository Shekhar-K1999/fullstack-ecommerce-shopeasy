package com.shekhar.shopeasy.validation;

public class EmailValidator {

    private EmailValidator() {
    }

    public static boolean isValid(String email) {

        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}