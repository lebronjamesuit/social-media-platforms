package com.general.business.resourceserver.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.general.business.resourceserver.dto.SubredditDto;
import com.general.business.resourceserver.service.SubredditService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest  // add (classes = GeneralApplication.class) if test and real are in different packages
@AutoConfigureMockMvc

public class SubRedditControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SubredditService service;

    public static final MediaType APPLICATION_JSON = MediaType.APPLICATION_JSON;
    private static final String END_POINT_PATH = "/api/v1/subreddit";

    @Test
    public void testRestGetAllSubreddits_200() throws Exception {

        //  When
        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

    }

    @Test
    public void testRestGetAllSubreddits_404() throws Exception {

        //  When
        mockMvc.perform(get(END_POINT_PATH+"/dummyPath"))
                .andExpect(status().isBadRequest())
            ;

    }

    @Test
    public void testGetSubredditByIdShouldReturn200Found() throws Exception {
        // Given
        SubredditDto subredditDto = new SubredditDto();
        subredditDto.setId(100l);
        subredditDto.setDescription("Desc");
        subredditDto.setName("News in Birmingham");

        // When
        Mockito.when(service.getSubreddit(100l)).thenReturn(subredditDto);

        mockMvc.perform(get(END_POINT_PATH + "/100"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("News in Birmingham")))
                .andDo(print())
        ;

        // Then Verify
        Mockito.verify(service, Mockito.times(1)).getSubreddit(100l);

    }

    @Test
    public void testPOSTSubredditShouldReturn200() throws Exception {
        // Given
        SubredditDto subredditDto = new SubredditDto();
        subredditDto.setId(2l);
        subredditDto.setDescription("Desc in Manchester");
        subredditDto.setName("Name in Manchester");

        String requestBody = objectMapper.writeValueAsString(subredditDto);

        // When   HTTP POST
        Mockito.when(service.save(subredditDto)).thenReturn(subredditDto);
        mockMvc.perform(
                post(END_POINT_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Name in Manchester")))
                .andExpect(jsonPath("$.description", is("Desc in Manchester")))
                .andDo(print())
        ;

        // Then Verify
        Mockito.verify(service, Mockito.times(1)).save(Mockito.any());
    }


}
