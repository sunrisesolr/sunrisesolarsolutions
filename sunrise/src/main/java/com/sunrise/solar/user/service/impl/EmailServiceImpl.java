package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithAttachment(String[] sendTo, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(text);

        // Add attachment
//        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//        helper.addAttachment("AttachmentName", file);

        javaMailSender.send(message);
    }
}
