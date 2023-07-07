package com.social.media.confessionmedia.exceptions;

import org.springframework.mail.MailException;
import org.springframework.security.core.AuthenticationException;

public class SocialGeneralException extends RuntimeException {

    public SocialGeneralException(String s, MailException e) {
        super(s, e);
    }

    public SocialGeneralException(String s, AuthenticationException e) {
        super(s, e);
    }

    public SocialGeneralException(String s) {
        super(s);
    }
}
