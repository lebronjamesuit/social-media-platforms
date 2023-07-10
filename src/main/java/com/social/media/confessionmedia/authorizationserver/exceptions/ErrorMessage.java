package com.social.media.confessionmedia.authorizationserver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private LocalDateTime localdatetime;
    private String message;
    private String details;

}

