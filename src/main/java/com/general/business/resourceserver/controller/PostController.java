package com.general.business.resourceserver.controller;

import com.general.business.resourceserver.dto.PostResponse;
import com.general.business.resourceserver.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
         return status(HttpStatus.OK).body( postService.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> postResponseList  =  postService.getAllPosts();
        return status(HttpStatus.OK).body(postResponseList);
    }

    @GetMapping(params = "subredditId")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@RequestParam Long subredditId) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
    }


    @GetMapping("/page")
    public ResponseEntity<List<PostResponse>> getAllPostsByPage(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        PageRequest pr = PageRequest.of(page-1,size, Sort.by(sort));
        return status(HttpStatus.OK).body(postService.getPostPaging(pr));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAllPostsByPage() {
        return status(HttpStatus.OK).body(postService.getNumberOfPosts());
    }
}
