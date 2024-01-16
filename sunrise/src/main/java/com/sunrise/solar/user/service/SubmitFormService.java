package com.sunrise.solar.user.service;

import com.sunrise.solar.user.dto.ResponseDTO;
import com.sunrise.solar.user.dto.SubmitFormDTO;

public interface SubmitFormService {
    void submitDetailsForm(SubmitFormDTO submitFormDTO) throws  Exception;
}
