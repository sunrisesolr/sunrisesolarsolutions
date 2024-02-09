package com.sunrise.solar.user.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendEmailWithAttachment(String[] sendTo, String subject, String text) throws MessagingException;
}
