package com.social.media.confessionmedia.controller;


import com.social.media.confessionmedia.config.JwtProvider;
import com.social.media.confessionmedia.config.Oauth2Config;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subreddit")
public class SubRedditController {

    private final Oauth2Config oauth2Config;
    private final JwtProvider jwtProvider;

    @GetMapping("/helloSubReddit")
    public ResponseEntity<String> getString(){

        return new ResponseEntity<String>("bang nhau", HttpStatus.ACCEPTED);

    }
}
