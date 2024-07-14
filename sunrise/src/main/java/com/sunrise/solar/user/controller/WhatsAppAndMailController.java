package com.sunrise.solar.user.controller;

import com.sunrise.solar.user.dto.FileUploadResponseDTO;
import com.sunrise.solar.user.dto.ResponseDTO;
import com.sunrise.solar.user.exception.ValidationException;
import com.sunrise.solar.user.helper.ResponseBuilderHelper;
import com.sunrise.solar.user.service.EmailService;
import com.sunrise.solar.user.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RequestMapping("/WhatsAppAndMailController")
@Controller
@Slf4j
public class WhatsAppAndMailController {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppAndMailController.class);
    @Autowired
    private EmailService emailService;


    @Autowired
    private UploadFileService uploadFileService;
    @Value("${emailids.submitForm.data}")
    private String sendToEmail;
    private final String  whatsappChat="WhatsappChat";


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
            log.error("exception occured while sneding  form data"+e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseBuilderHelper.buildFailureSubmitFormResponse(null));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBuilderHelper.buildSubmitFormResponse(null));
    }


    @PostMapping(value = "/uploadLeadFile/upload")
    public @ResponseBody
    FileUploadResponseDTO uploadCustomerDetails(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        FileUploadResponseDTO fileUploadResponseDTO=new FileUploadResponseDTO();
        try {
            fileUploadResponseDTO.setFileName(name);
            uploadFileService.uploadFile(name, file);
            fileUploadResponseDTO.setStatus("Sucess");
            fileUploadResponseDTO.setMessage("Successfully uploaded"+name+"xlsx file.");

            log.info("Successfully uploaded bse_scheme_master xlsx file.");
        } catch (Exception e) {
            fileUploadResponseDTO.setStatus("Failed");
            fileUploadResponseDTO.setMessage(e.getMessage());
            return fileUploadResponseDTO;
        }
        return fileUploadResponseDTO;
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWhatsAppEvent(@RequestBody String payload) {
        try {
            emailService.sendEmail(sendToEmail.split(","), whatsappChat, payload);
            return ResponseEntity.ok("Event processed successfully");
        } catch (Exception e) {
            log.error("Error processing WhatsApp event: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error processing event", e.getMessage()));
        }
    }



}
