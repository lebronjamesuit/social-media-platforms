package com.social.media.confessionmedia.resourceserver.controller;

import com.social.media.confessionmedia.resourceserver.caches.CachePostManager;
import com.social.media.confessionmedia.resourceserver.dto.PostRequest;
import com.social.media.confessionmedia.resourceserver.dto.PostResponse;
import com.social.media.confessionmedia.resourceserver.service.PostService;
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

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> postResponseList =  CachePostManager.getPostFromCached();
        if(postResponseList == null){
            postResponseList =  postService.getAllPosts();
            CachePostManager.putDataToCache(postResponseList);
        }
        return status(HttpStatus.OK).body(postResponseList);
    }

    @GetMapping(params = "subredditId")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@RequestParam Long subredditId) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
    }

    @GetMapping(params = "username")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@RequestParam String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
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
