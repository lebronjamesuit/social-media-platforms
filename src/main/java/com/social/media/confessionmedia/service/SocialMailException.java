package com.social.media.confessionmedia.service;

import org.springframework.mail.MailException;

public class SocialMailException extends RuntimeException {

    public SocialMailException(String s, MailException e) {
        super(s, e);
    }

    public SocialMailException(String s) {
        super(s);
    }
}
