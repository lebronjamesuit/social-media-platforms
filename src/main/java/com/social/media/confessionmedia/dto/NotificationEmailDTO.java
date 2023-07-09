package com.social.media.confessionmedia.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotificationEmailDTO {

    private String recipient;
    private String subject;
    private String body;
}
