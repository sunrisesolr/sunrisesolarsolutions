package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.SubmitFormEntity;
import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import com.sunrise.solar.user.service.EmailService;
import com.sunrise.solar.user.service.SubmitFormService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SubmitFormServiceImpl implements SubmitFormService {

    @Autowired
    private SubmitFormRepo submitFormRepo;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public void submitDetailsForm(SubmitFormDTO submitFormDTO) throws MessagingException {

        log.info("inside submitDetailsForm started with data"+submitFormDTO.toString());

        try
        {
            StringBuilder sb=new StringBuilder();
            sb.append(submitFormDTO.getLocation());
            sb.append("\n\n\n");
            sb.append(submitFormDTO.getPhoneNumber());
            sb.append("\n\n\n");
            sb.append(submitFormDTO.getComments());

            String[] sendTo = submitFormDTO.getEmailAddress().split(",");

            emailService.sendEmailWithAttachment(sendTo,"NEW LEAD FOR SOLAR",sb.toString());
            SubmitFormEntity submitFormEntity=convertDTOToEntity(submitFormDTO);
            submitFormRepo.save(submitFormEntity);
        }
        catch (Exception e)
        {
            log.error("error occur while saving form data"+e.getMessage());
            throw  e;
        }

        log.info("inside submitDetailsForm end with data"+submitFormDTO.toString());


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
