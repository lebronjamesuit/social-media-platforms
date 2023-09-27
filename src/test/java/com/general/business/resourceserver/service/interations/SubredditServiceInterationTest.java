package com.general.business.resourceserver.service.interations;

import com.general.business.resourceserver.dto.SubredditDto;
import com.general.business.resourceserver.service.SubredditService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;


@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SubredditServiceInterationTest {

    // Real Service to interation test
    @Autowired
    private SubredditService subredditServiceReal;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.create.subreddit}")
    private String sqlAddSubreddit;

    @Value("${sql.script.delete.subreddit}")
    private String sqlDeleteSubreddit;

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddSubreddit);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteSubreddit);
    }

    @SqlGroup({ @Sql(scripts = "/data-test.sql", config = @SqlConfig(commentPrefix = "`")) })
    @Test
    public void saveSubRedditByServiceTest() throws Exception {

        SubredditDto subredditDto = new SubredditDto();
        subredditDto.setDescription("Desc in Bath");
        subredditDto.setName("Name in Bath");

        SubredditDto returnSubredditDto = subredditServiceReal.save(subredditDto);

        assertEquals(subredditDto.getDescription(), returnSubredditDto.getDescription());
        System.out.println(returnSubredditDto.getDescription());
    }


}
