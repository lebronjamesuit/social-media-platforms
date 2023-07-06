package com.social.media.confessionmedia.service;

import org.springframework.mail.MailException;

public class SocialGeneralException extends RuntimeException {

    public SocialGeneralException(String s, MailException e) {
        super(s, e);
    }

    public SocialGeneralException(String s) {
        super(s);
    }
}
