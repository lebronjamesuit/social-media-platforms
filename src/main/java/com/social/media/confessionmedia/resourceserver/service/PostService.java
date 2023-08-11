package com.social.media.confessionmedia.resourceserver.service;



import com.social.media.confessionmedia.authorizationserver.repository.UserRepo;
import com.social.media.confessionmedia.authorizationserver.service.AuthService;
import com.social.media.confessionmedia.resourceserver.dto.PostRequest;

import com.social.media.confessionmedia.resourceserver.dto.PostResponse;
import com.social.media.confessionmedia.resourceserver.exceptions.PostNotFoundException;
import com.social.media.confessionmedia.resourceserver.exceptions.SubredditNotFoundException;
import com.social.media.confessionmedia.resourceserver.mapper.PostMapper;

import com.social.media.confessionmedia.resourceserver.model.Post;
import com.social.media.confessionmedia.resourceserver.model.Subreddit;
import com.social.media.confessionmedia.resourceserver.repository.PostRepository;
import com.social.media.confessionmedia.resourceserver.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.social.media.confessionmedia.authorizationserver.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import com.social.media.confessionmedia.resourceserver.model.Post;
import com.social.media.confessionmedia.resourceserver.dto.PostResponse;
import com.social.media.confessionmedia.resourceserver.exceptions.PostNotFoundException;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepo userRepo;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
        public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));

        return postRepository.findByUser(userOptional.get())
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }


}
