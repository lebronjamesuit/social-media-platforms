package com.general.business.resourceserver.controller;


import com.general.business.resourceserver.service.CommentService;
import com.general.business.resourceserver.dto.CommentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }

    // api/v1/comments/postId/{postId}
    @GetMapping( "/postId/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }



}