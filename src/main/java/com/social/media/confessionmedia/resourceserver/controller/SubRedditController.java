package com.social.media.confessionmedia.resourceserver.controller;


import com.social.media.confessionmedia.authorizationserver.config.Oauth2Config;
import com.social.media.confessionmedia.authorizationserver.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
