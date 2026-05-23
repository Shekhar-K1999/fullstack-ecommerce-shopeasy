package com.shekhar.shopeasy.validation;

public class PhoneValidator {

    private PhoneValidator() {
    }

    public static boolean isValid(String phone) {

        return phone.matches("^[6-9]\\d{9}$");
    }
}