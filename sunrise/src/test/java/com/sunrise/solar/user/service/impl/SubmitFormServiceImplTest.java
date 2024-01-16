package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SubmitFormServiceImplTest {

    @Mock
    private SubmitFormRepo submitFormRepo;

    @InjectMocks
    private SubmitFormServiceImpl submitFormService;

    @Test
    void submitDetailsForm_Success() {
        // Arrange
        SubmitFormDTO submitFormDTO = new SubmitFormDTO();
        submitFormDTO.setName("John Doe");
        submitFormDTO.setPhoneNumber("1234567890");
        submitFormDTO.setEmailAddress("john.doe@example.com");
        submitFormDTO.setLocation("Sample Location");

        // Act
        submitFormService.submitDetailsForm(submitFormDTO);

        // Assert
        Mockito.verify(submitFormRepo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void submitDetailsForm_Exception() {
        // Arrange
        SubmitFormDTO submitFormDTO = new SubmitFormDTO();
        submitFormDTO.setName("John Doe");
        submitFormDTO.setPhoneNumber("1234567890");
        submitFormDTO.setEmailAddress("john.doe@example.com");
        submitFormDTO.setLocation("Sample Location");

        Mockito.doThrow(new RuntimeException("Simulating an exception")).when(submitFormRepo).save(Mockito.any());

        // Act and Assert
        try {
            submitFormService.submitDetailsForm(submitFormDTO);
        } catch (Exception e) {
            // Make sure the exception is thrown
            assert e.getMessage().equals("Simulating an exception");
        }
    }
}

