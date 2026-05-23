package com.shekhar.shopeasy.validation;

public class PasswordValidator {

    private PasswordValidator() {
    }

    public static boolean isValid(String password) {

        return password.matches(
                "^(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=!])" +
                "(?=\\S+$).{8,20}$"
        );
    }
}