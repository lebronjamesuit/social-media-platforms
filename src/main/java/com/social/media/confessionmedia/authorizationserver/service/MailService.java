package com.social.media.confessionmedia.authorizationserver.service;

import com.social.media.confessionmedia.authorizationserver.builder.MailContentBuilder;
import com.social.media.confessionmedia.authorizationserver.dto.NotificationEmailDTO;
import com.social.media.confessionmedia.authorizationserver.exceptions.SocialGeneralException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
