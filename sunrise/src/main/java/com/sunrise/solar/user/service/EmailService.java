package com.sunrise.solar.user.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendEmail(String[] sendTo, String subject, String text) throws MessagingException;
    void sendMailToUser();
}
