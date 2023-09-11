package com.social.media.confessionmedia.resourceserver.service;

import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.authorizationserver.repository.UserRepo;
import com.social.media.confessionmedia.authorizationserver.service.AuthService;
import com.social.media.confessionmedia.resourceserver.dto.PostResponse;
import com.social.media.confessionmedia.resourceserver.exceptions.PostNotFoundException;
import com.social.media.confessionmedia.resourceserver.mapper.PostMapper;
import com.social.media.confessionmedia.resourceserver.model.Post;
import com.social.media.confessionmedia.resourceserver.repository.PostPagingRepository;
import com.social.media.confessionmedia.resourceserver.repository.PostRepository;
import com.social.media.confessionmedia.resourceserver.repository.SubredditRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostPagingRepository postPagingRepository;

    @Mock
    private SubredditRepository subredditRepository;

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthService authService;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;


    /* Inject Mocks equals with
     this.postService = new PostService(postRepository, postPagingRepository,
                   subredditRepository, userRepo, authService, postMapper) ;*/


    @Test
    void shouldFindPostWithId() {
        // Given
          // Post
        Post post = Post.builder().postId(1l).postName("postname")
                .description("desc")
                .user(new User()).build();

           // Post Response
        PostResponse postResponse  = new PostResponse();
        postResponse.setPostName("postname");
        postResponse.setDescription("desc");
        postResponse.setUserName("");

        // When
        Mockito.when(postRepository.findById(1l)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(postResponse);

        // Call
        PostResponse result =  postService.getPost(1l);

        // Then
        assertNotNull(result);

    }
}