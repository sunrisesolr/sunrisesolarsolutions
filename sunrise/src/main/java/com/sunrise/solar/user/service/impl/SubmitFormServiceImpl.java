package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.SubmitFormEntity;
import com.sunrise.solar.user.dto.ResponseDTO;
import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.helper.ResponseBuilderHelper;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import com.sunrise.solar.user.service.SubmitFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SubmitFormServiceImpl implements SubmitFormService {

    @Autowired
    private SubmitFormRepo submitFormRepo;

    @Override
    @Transactional
    public void submitDetailsForm(SubmitFormDTO submitFormDTO) {
        try
        {
            SubmitFormEntity submitFormEntity=convertDTOToEntity(submitFormDTO);
            submitFormRepo.save(submitFormEntity);
        }
        catch (Exception e)
        {
            log.error("error occure while saving form data"+e.getMessage());
            throw  e;
        }


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
