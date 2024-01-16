package com.sunrise.solar.user.controller;

import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.helper.ResponseBuilderHelper;
import com.sunrise.solar.user.service.SubmitFormService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequestMapping("/form")
public class SubmitFormController {
    @Autowired
    private SubmitFormService submitFormService;

    @RequestMapping(value = "/submitForm", method = RequestMethod.POST)
    public ResponseEntity<?> submitForm(
            @RequestBody @NotNull @Valid SubmitFormDTO submitFormDTO) {
        try
        {
            submitFormService.submitDetailsForm(submitFormDTO);
        }
        catch (Exception e)
        {
            log.error("exception occured while saving form data"+e.getMessage());
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilderHelper.buildFailureSubmitFormResponse(submitFormDTO));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilderHelper.buildSubmitFormResponse(submitFormDTO));
    }
}
