package com.social.media.confessionmedia.authorizationserver.service;

import com.social.media.confessionmedia.authorizationserver.builder.MailContentBuilder;
import com.social.media.confessionmedia.authorizationserver.dto.NotificationEmailDTO;
import com.social.media.confessionmedia.authorizationserver.exceptions.SocialGeneralException;
import com.social.media.confessionmedia.authorizationserver.model.User;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

// Localhost development environment only
@Slf4j
@Service
public class MailService {

    private MailContentBuilder mailContentBuilder;

    private JavaMailSender javaMailSender;

    @Value("${server.host.url.production}")
    private String productionURL;

    // Constructor for dependencies injection
    public MailService(MailContentBuilder mailContentBuilder, JavaMailSender javaMailSender){
        this.mailContentBuilder = mailContentBuilder;
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(NotificationEmailDTO notificationEmailDTO) {

        MimeMessagePreparator mimeMessagePreparator = buildMimeMessagePreparator(notificationEmailDTO);
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("send email ok");
        }catch (MailException e){
            log.error(e.getMessage());
            throw new SocialGeneralException("Exception occurred when sending mail to " + notificationEmailDTO.getRecipient(), e);
        }

    }

    private MimeMessagePreparator buildMimeMessagePreparator(NotificationEmailDTO notificationEmailDTO) {
        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setFrom("social-media@confession.com");
                helper.setTo(notificationEmailDTO.getRecipient());
                helper.setSubject(notificationEmailDTO.getSubject());
                helper.setText(mailContentBuilder.build(notificationEmailDTO.getBody()));
            }
        };
        return mimeMessagePreparator;
    }

    public void sendRegisterConfirmationEmail(User user, String tokenValue) {
        NotificationEmailDTO notifiedEmail = new NotificationEmailDTO();
        notifiedEmail.setRecipient(user.getEmail());
        notifiedEmail.setSubject("Confirmation registration");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Welcome to our social media:  " + user.getUserName());
        stringBuilder.append("One more step is click the link bellow to activate your account : ");
        stringBuilder.append(productionURL + "/api/auth/accountVerification/" + tokenValue);

        notifiedEmail.setBody(stringBuilder.toString());
        sendEmail(notifiedEmail);
    }
}
