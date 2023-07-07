package com.social.media.confessionmedia.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/subreddit")
public class SubRedditController {

    @GetMapping("/helloSubReddit")
    public ResponseEntity<String> getString(){
        return new ResponseEntity<String>("this path is helloSubReddit",HttpStatus.ACCEPTED);
    }
}
