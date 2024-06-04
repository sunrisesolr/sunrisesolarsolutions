package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.SubmitFormEntity;
import com.sunrise.solar.user.constants.AppConstants;
import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.enums.ErrorDefinition;
import com.sunrise.solar.user.exception.ValidationException;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import com.sunrise.solar.user.service.EmailService;
import com.sunrise.solar.user.service.SubmitFormService;
import com.sunrise.solar.user.validation.ValidationUtils;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SubmitFormServiceImpl implements SubmitFormService {

    @Autowired
    private SubmitFormRepo submitFormRepo;

    @Autowired
    private EmailService emailService;

    @Value("${emailids.submitForm.data}")
    private String sendToEmail;

    @Override
    @Transactional
    public void submitDetailsForm(SubmitFormDTO submitFormDTO) throws MessagingException,ValidationException {

        log.info("inside submitDetailsForm started with data"+submitFormDTO.toString());

        if(!ValidationUtils.isEmailValid(submitFormDTO.getEmailAddress()))
        {
            log.error("email provided is not correct"+submitFormDTO.getEmailAddress());
            throw new ValidationException(ErrorDefinition.INVALID_EMAIL);
        }

        if(!ValidationUtils.isPhoneValid(submitFormDTO.getPhoneNumber()))
        {
            log.error("phone number  provided is not correct"+submitFormDTO.getPhoneNumber());
            throw new ValidationException(ErrorDefinition.INVALID_PHONE);
        }

	// save to db
	try
	{
	    SubmitFormEntity submitFormEntity=convertDTOToEntity(submitFormDTO);
            submitFormRepo.save(submitFormEntity);
        }
        catch (Exception e)
        {
            log.error("error occur while saving form data: "+e.getMessage());
            throw  e;
        }

	// try to send email, ok if doesnt succeed
        try
        {
            String emailBody=createEmailBody(submitFormDTO);
            String[] recipientsEmail=sendToEmail.split(",");
            emailService.sendEmail(recipientsEmail, AppConstants.NEW_SOLAR_LEAD,emailBody);
	}
	catch (Exception e){
	    log.error("Could not send email: " + e.getMessage());
	}


        log.info("inside submitDetailsForm end with data"+submitFormDTO.toString());


    }

    private String createEmailBody(SubmitFormDTO submitFormDTO) {

        StringBuilder emailBody=new StringBuilder();
        emailBody.append(submitFormDTO.getLocation());
        emailBody.append("\n\n\n");
        emailBody.append(submitFormDTO.getPhoneNumber());
        emailBody.append("\n\n\n");
        emailBody.append(submitFormDTO.getComments());
        emailBody.append("\n\n\n");
        emailBody.append(submitFormDTO.getEmailAddress());

        return emailBody.toString();

    }

    private SubmitFormEntity convertDTOToEntity(SubmitFormDTO submitFormDTO) {
        SubmitFormEntity submitFormEntity=new SubmitFormEntity();
        submitFormEntity.setComments(submitFormDTO.getComments());
        submitFormEntity.setName(submitFormDTO.getName());
        submitFormEntity.setPhoneNumber(submitFormDTO.getPhoneNumber());
        submitFormEntity.setEmail(submitFormDTO.getEmailAddress());
        submitFormEntity.setLocation(submitFormDTO.getLocation());
        return submitFormEntity;

    }
}
