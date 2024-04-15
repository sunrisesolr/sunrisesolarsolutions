package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.dto.FetchLeadDTO;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import com.sunrise.solar.user.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private SubmitFormRepo submitFormRepo;


    @Override
    public FetchLeadDTO fetchLead() {

        submitFormRepo.findAll();
        return null;
    }
}
