package com.sunrise.solar.user.exception;

import com.sunrise.solar.user.enums.ErrorDefinition;
import lombok.Getter;

@Getter
public class ValidationException extends  RuntimeException{

    private String code;
    private String message;
    private ErrorDefinition error;


    public ValidationException(ErrorDefinition errorDefinition)
    {
        this.error=errorDefinition;
        this.code=errorDefinition.getCode();
        this.message=errorDefinition.getMessage();

    }

    public ValidationException(String code,String message)
    {
        this.code=code;
        this.message=message;

    }


    public String getCode() {
        return code;
    }
}
