package com.sunrise.solar.user.validation;

import com.sunrise.solar.user.constants.AppConstants;

import java.util.regex.Pattern;

public class ValidationUtils {

    public  static boolean isEmailValid(String email) {
        return Pattern.compile(AppConstants.EMAIL_VALIDATION_REGEX)
                .matcher(email)
                .matches();
    }


    public static boolean isPhoneValid(String phone) {
        return Pattern.compile(AppConstants.PHONE_VALIDATION_REGEX)
                .matcher(phone)
                .matches();
    }



}
