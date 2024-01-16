package com.sunrise.solar.user.helper;

import com.sunrise.solar.user.dto.ResponseDTO;
import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.enums.SubmitFormStatus;

public class ResponseBuilderHelper {

    public static ResponseDTO  buildSubmitFormResponse(SubmitFormDTO submitFormDTO)
    {
        return ResponseDTO.builder().comments(submitFormDTO.getComments())
                .emailAddress(submitFormDTO.getEmailAddress()).phoneNumber(submitFormDTO.getPhoneNumber()).
                name(submitFormDTO.getName()).location(submitFormDTO.getLocation()).status(SubmitFormStatus.SUCCESS.toString()).build();


    }

    public static ResponseDTO  buildFailureSubmitFormResponse(SubmitFormDTO submitFormDTO)
    {
        return ResponseDTO.builder().comments(submitFormDTO.getComments())
                .emailAddress(submitFormDTO.getEmailAddress()).phoneNumber(submitFormDTO.getPhoneNumber()).
                name(submitFormDTO.getName()).location(submitFormDTO.getLocation()).status(SubmitFormStatus.FAILED.toString()).build();


    }



}
