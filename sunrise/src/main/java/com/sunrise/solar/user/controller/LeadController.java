package com.sunrise.solar.user.controller;

import com.sunrise.solar.user.dto.ResponseDTO;
import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.exception.ValidationException;
import com.sunrise.solar.user.helper.ResponseBuilderHelper;
import com.sunrise.solar.user.service.EmailService;
import com.sunrise.solar.user.service.LeadService;
import com.sunrise.solar.user.service.SubmitFormService;
import com.sunrise.solar.user.service.UploadFileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RequestMapping("/lead")
@Controller
public class LeadController {

    @Autowired
    private LeadService leadService;

    @Autowired
    private EmailService emailService;


    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/fetchLead", method = RequestMethod.GET)
    public ResponseEntity<?> submitForm(
            @RequestBody @NotNull @Valid SubmitFormDTO submitFormDTO) {

        log.info("request for submit form with data"+submitFormDTO.toString());
        try
        {
            leadService.fetchLead();
        }
        catch (ValidationException e)
        {
            log.error("exception occured while validation "+e.getMessage());
            throw new ValidationException(e.getCode(),e.getMessage());
        }
        catch (Exception e)
        {
            log.error("exception occured while saving form data"+e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseBuilderHelper.buildFailureSubmitFormResponse(submitFormDTO));
        }
        log.info("request for submit form with data"+submitFormDTO.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilderHelper.buildSubmitFormResponse(submitFormDTO));
    }


    @PostMapping(value = "/form/sendMailToUser", produces = {"application/json"})
    public ResponseEntity<?> sendMailToUser() {

        log.info("inside sendMailToUser triggered ");
        try
        {
            emailService.sendMailToUser();
        }
        catch (ValidationException e)
        {
            log.error("exception occured while validation "+e.getMessage());
            throw new ValidationException(e.getCode(),e.getMessage());
        }
        catch (Exception e)
        {
            log.error("exception occured while saving form data"+e.getMessage());
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseBuilderHelper.buildFailureSubmitFormResponse(submitFormDTO));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilderHelper.buildSubmitFormResponse(null));
    }


    @PostMapping(value = "/uploadLeadFile/upload")
    public @ResponseBody
    ResponseDTO uploadBseSchemeMasterTransactionData(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        ResponseDTO responseDTO=new ResponseDTO();
        try {
              uploadFileService.uploadFile(name, file);
//            if(responseDTO.getMetaDTO().getCode().equals(SuccessCode.PM_DF_SC_101.toString())) {
//                logger.info("Successfully uploaded bse_scheme_master xlsx file.");
//            }
        } catch (Exception e) {
            responseDTO.setComments(e.getMessage());
//            logger.error("{}. Detailed cause of failure: {} exception: {}.",ErrorCode.SCHEME_MASTER_UPLOAD_FAILED.getMessage(),responseDTO.getData(),e);
//            responseDTO.setMetaDTO(new MetaDTO(ErrorCode.SCHEME_MASTER_UPLOAD_FAILED.toString(),ErrorCode.SCHEME_MASTER_UPLOAD_FAILED.getMessage(), UUID.randomUUID().toString()));
        }
        return responseDTO;
    }







}
