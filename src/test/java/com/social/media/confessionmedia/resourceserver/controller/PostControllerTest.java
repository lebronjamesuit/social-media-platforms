package com.social.media.confessionmedia.resourceserver.controller;

import com.social.media.confessionmedia.resourceserver.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPost() {

    }
}