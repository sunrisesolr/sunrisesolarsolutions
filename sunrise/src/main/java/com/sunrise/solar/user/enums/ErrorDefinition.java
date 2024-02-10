package com.sunrise.solar.user.enums;

import lombok.Getter;

@Getter
public enum ErrorDefinition {

    INVALID_EMAIL("Invalid Email","email address provided by the user is incorrect please provide the correct email"),
    INVALID_PHONE("Invalid Phone","Phone Number provided by the user is incorrect please provide the correct Phone Number");

    private String code;
    private String message;

    ErrorDefinition(String code){
        this.code = code;
    }

    ErrorDefinition(String code, String message){
        this.code = code;
        this.message = message;
    }

}
