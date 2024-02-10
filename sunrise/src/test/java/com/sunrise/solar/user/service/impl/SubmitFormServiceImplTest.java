package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.dto.SubmitFormDTO;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import com.sunrise.solar.user.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubmitFormServiceImplTest {
    @Mock
    private SubmitFormRepo submitFormRepo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SubmitFormServiceImpl submitFormService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Value("${emailids.submitForm.data}")
    private String[] sendToEmail;



//    @Test
    void submitDetailsForm_ValidInput_EmailSentAndFormSaved() throws MessagingException {
        // Arrange
        SubmitFormDTO submitFormDTO = new SubmitFormDTO();
        submitFormDTO.setName("John Doe");
        submitFormDTO.setEmailAddress("john@example.com");
        submitFormDTO.setPhoneNumber("1234567890");
        submitFormDTO.setLocation("New York");
        submitFormDTO.setComments("Test comment");

        // Act
        submitFormService.submitDetailsForm(submitFormDTO);

        // Assert
        verify(emailService).sendEmail(any(),anyString(), anyString());
        verify(submitFormRepo).save(any());
    }

//    @Test
    void submitDetailsForm_InvalidEmailAddress_NoEmailSent() throws MessagingException {
        // Arrange
        SubmitFormDTO submitFormDTO = new SubmitFormDTO();
        submitFormDTO.setEmailAddress("invalid-email");
//         when(emailService.sendEmail(any(), any(), any())).thenReturn();

        // Act
        submitFormService.submitDetailsForm(submitFormDTO);

        // Assert
        verify(emailService, never()).sendEmail(any(), any(), any());
        verify(submitFormRepo, never()).save(any());
    }

//    @Test
    void submitDetailsForm_ExceptionThrownInEmailService_FormNotSaved() throws MessagingException {
        // Arrange
        SubmitFormDTO submitFormDTO = new SubmitFormDTO();
        submitFormDTO.setEmailAddress("john@example.com");

        doThrow(new MessagingException("Failed to send email")).when(emailService)
                .sendEmail(any(), any(), any());

        // Act & Assert
        try {
            submitFormService.submitDetailsForm(submitFormDTO);
        } catch (MessagingException e) {
            // Expected exception
        }

        verify(submitFormRepo, never()).save(any());
    }

}

