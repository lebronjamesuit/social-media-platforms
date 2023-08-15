package com.social.media.confessionmedia.authorizationserver.service;

import com.social.media.confessionmedia.authorizationserver.builder.MailContentBuilder;
import com.social.media.confessionmedia.authorizationserver.dto.NotificationEmailDTO;
import com.social.media.confessionmedia.authorizationserver.exceptions.SocialGeneralException;
import com.social.media.confessionmedia.authorizationserver.model.User;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class GmailService {

    private JavaMailSender mailSender;

    @Value("${server.host.url.production}")
    private String productionURL;

    // Constructor
    public GmailService(JavaMailSender sender){
        this.mailSender = sender;
    }

    @Async
    public void sendRegisterConfirmationEmail(User user, String tokenValue) {

        String toEmail = user.getEmail();
        String subject = "Confirmation registration";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hi, ");
        stringBuilder.append(" Welcome to our social media:  " + user.getUserName());
        stringBuilder.append(" One more step is click the link bellow to activate your account : ");
        stringBuilder.append(productionURL+"/api/auth/accountVerification/" + tokenValue);
        String body = stringBuilder.toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jamesfloatingmarket1508@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");
    }


}
