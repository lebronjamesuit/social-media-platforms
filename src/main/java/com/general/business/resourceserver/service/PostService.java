package com.general.business.resourceserver.service;

import com.general.business.resourceserver.dto.PostResponse;
import com.general.business.resourceserver.exceptions.PostNotFoundException;
import com.general.business.resourceserver.exceptions.SubredditNotFoundException;
import com.general.business.resourceserver.mapper.PostMapper;

import com.general.business.resourceserver.model.Post;
import com.general.business.resourceserver.model.Subreddit;
import com.general.business.resourceserver.repository.PostPagingRepository;
import com.general.business.resourceserver.repository.PostRepository;
import com.general.business.resourceserver.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostPagingRepository postPagingRepository;
    private final SubredditRepository subredditRepository;
    private final PostMapper postMapper;


    @Transactional(readOnly = true)
        public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByPostIdDesc()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public long getNumberOfPosts() {
        return postRepository.count();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostPaging(PageRequest pr) {
        Page<Post> posts = postPagingRepository.findAll(pr);
        return posts.stream()
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

}
