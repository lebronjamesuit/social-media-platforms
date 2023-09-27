package com.general.business.resourceserver.controller;

import com.general.business.resourceserver.dto.SubredditDto;
import com.general.business.resourceserver.service.SubredditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subreddit")
@RequiredArgsConstructor
public class SubRedditController {

    private final SubredditService subredditService;


    @GetMapping("/hello")
    public ResponseEntity<String> hello(){

        return ResponseEntity.status(HttpStatus.OK).body("Hello yourself");
    }

    @GetMapping
    public List<SubredditDto> getAllSubreddits() {
        return subredditService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid SubredditDto subredditDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.save(subredditDto));
    }


}
