package com.social.media.confessionmedia.authorizationserver.exceptions;

import org.springframework.security.oauth2.jwt.BadJwtException;

public class TokenException extends RuntimeException {

    public TokenException(String s) {
        super(s);
    }

    public TokenException(BadJwtException e, String s) {
        super(s, e);
    }

}
