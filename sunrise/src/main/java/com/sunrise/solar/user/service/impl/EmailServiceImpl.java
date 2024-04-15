package com.sunrise.solar.user.service.impl;

import com.sunrise.solar.user.SubmitFormEntity;
import com.sunrise.solar.user.repo.SubmitFormRepo;
import com.sunrise.solar.user.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {


    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private final RestTemplate restTemplate;

    public EmailServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public static final int PAGE_SIZE = 50;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SubmitFormRepo submitFormRepo;

    static  final String  pathToAttachment = "/Users/harendersingh/Desktop/CompanyLogo.png";

    @Override
    public void sendEmail(String[] sendTo, String subject, String text) throws MessagingException {
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

    @Override
    public void sendMailToUser() {
        System.out.println("inside sendMailToUser triggered ");
        Long totalCount = submitFormRepo.count();
        int pageSize = this.PAGE_SIZE;
        int pages = (totalCount.intValue()/pageSize) + 1;
        int pageIndex;

        PageRequest pageRequest;
        for (int pageNumber = 1; pageNumber <= pages; pageNumber++) {
            pageIndex = pageNumber - 1;
            pageRequest = PageRequest.of(pageIndex, pageSize);
            Page<SubmitFormEntity> submitFormEntityPage=submitFormRepo.findAll(pageRequest);
            List<SubmitFormEntity> submitFormEntityList = submitFormEntityPage.getContent();
            int count= 0;
            int j=0;
            String[] sendTo = new String[0];
            for(int i=0;i<=submitFormEntityList.size() && submitFormEntityList.size()-i>0;i++){

                sendMessage(submitFormEntityList.get(i).getPhoneNumber());
                if(j==0)
                    sendTo= new String[Math.min(100,(submitFormEntityList.size()-i))];
                sendTo[j]=submitFormEntityList.get(i).getEmail();
                j++;
                if(j==sendTo.length) {
                    j=0;
                    createAndSendEmail(submitFormEntityList.get(i));
                    count++;
                    log.error("total messages send to user " + count + "email " + submitFormEntityList.get(i).getEmail());
                }
            }

//            submitFormEntityList.stream().forEach(entity -> {
//                createAndSendEmail(entity);
//                count.getAndIncrement();
//                log.error("total messages send to user "+count.toString()+"email "+entity.getEmail());
//            });


        }
    }

    private void createAndSendEmail(SubmitFormEntity entity) {
        String[] sendTo = new String[1];
        sendTo[0] = entity.getEmail();
        String subject = "Get Free Solar Electricity with Us";
        String text = generateEmailMessage(entity.getName(), "Sunrise Solar Solutions", "Harender Singh", "CEO", "7818886988");
        try {
            sendEmail(sendTo, subject, text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailWithAttachement(String[] sendTo, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(text);

        // Add attachment
        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("AttachmentName", file);

        javaMailSender.send(message);
    }


    public static String generateEmailMessage(String customerName, String companyName,
                                              String yourName, String yourPosition, String yourContactInfo) {
        StringBuilder message = new StringBuilder();
        message.append("Subject: Embrace the Power of Solar Energy for a Brighter Tomorrow!\n\n");
        message.append("Dear ").append(customerName).append(",\n\n");
        message.append("I hope this message finds you well. As a valued customer, we wanted to reach out and share an exciting opportunity that could not only benefit you but also contribute positively to the environment: solar energy.\n\n");
        message.append("At ").append(companyName).append(", we are passionate about sustainability and providing innovative solutions to help you save on energy costs while reducing your carbon footprint. With solar energy, you have the opportunity to harness the power of the sun to generate clean, renewable electricity for your home or business.\n\n");
        message.append("Here are just a few reasons why solar energy might be the right choice for you:\n\n");
        message.append("1. Savings on Energy Bills: By generating your own electricity from sunlight, you can significantly reduce or even eliminate your monthly energy bills.\n");
        message.append("2. Environmental Benefits: Solar energy is clean and renewable, which means it produces no harmful emissions and helps combat climate change.\n");
        message.append("3. Energy Independence: With solar panels installed, you become less reliant on traditional energy sources, giving you greater control over your energy consumption.\n");
        message.append("4. Financial Incentives: There are often various financial incentives, such as tax credits and rebates, available for installing solar panels, making it an even more attractive investment.\n");
        message.append("5. Increase Property Value: Solar panels can increase the value of your property, making it more attractive to potential buyers should you decide to sell in the future.\n\n");
        message.append("If you're interested in learning more about how solar energy can benefit you and your home/business, please don't hesitate to reach out. Our team of experts is here to answer any questions you may have and provide a personalized assessment to determine the best solar solution for your needs.\n\n");
        message.append("Let's work together to create a brighter, more sustainable future. Thank you for considering solar energy with us.\n\n");
        message.append("Warm regards,\n");
        message.append(yourName).append("\n");
        message.append(yourPosition).append("\n");
        message.append(yourContactInfo).append("\n");
        message.append(companyName);

        return message.toString();
    }


    public void sendMessage(String recipientId) {
        try {
            String url = "https://graph.facebook.com/v18.0/249395708263058/messages";
            String accessToken = "EAANvFeZBbAaUBOx9dDogkZCdAQugUPQLRP0JK74Lrj7Am5rZCbZCOl4t33jVVZCZCrgaQXy8mDUGoyKyS8qJyScZAG7ZAGNQ6fHnZACLYvnqbdCDZAb6LiEFysrRBGc2mC7wlbLAhveBs8PYoHPZASz5rImN16QiYo6gu95t4ZCEC62shZCafGBjwEFRqAzlrRJoA3KwqaXutkDzazEi2nTW1nGfP87zo2QMZD";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"messaging_product\": \"whatsapp\", \"to\": \"" + recipientId + "\", \"type\": \"template\", \"template\": { \"name\": \"solarmarketting\", \"language\": { \"code\": \"en_US\" } } }";

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Message sent successfully
            } else {
                // Handle error
            }
        }
        catch(Exception e)
        {
            log.error("error sendng whtasapp message to "+recipientId);
        }
        log.error("succesfully send  whtasapp message to "+recipientId);
    }


}
