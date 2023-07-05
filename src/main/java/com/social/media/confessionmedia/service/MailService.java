package com.social.media.confessionmedia.service;

import com.social.media.confessionmedia.builder.MailContentBuilder;
import com.social.media.confessionmedia.dto.NotificationEmail;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class MailService {

    private MailContentBuilder mailContentBuilder;

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(NotificationEmail notificationEmail) {

        MimeMessagePreparator mimeMessagePreparator = buildMimeMessagePreparator(notificationEmail);
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("send email ok");
        }catch (MailException e){
            log.error(e.getMessage());
            throw new SocialMailException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }

    }

    private MimeMessagePreparator buildMimeMessagePreparator(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setFrom("social-media@confession.com");
                helper.setTo(notificationEmail.getRecipient());
                helper.setSubject(notificationEmail.getSubject());
                helper.setText(mailContentBuilder.build(notificationEmail.getBody()));
            }
        };
        return mimeMessagePreparator;
    }

}
