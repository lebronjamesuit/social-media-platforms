package com.general.business.resourceserver.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.general.business.resourceserver.dto.SubredditDto;
import com.general.business.resourceserver.mapper.SubredditMapper;
import com.general.business.resourceserver.model.Subreddit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

// Wanted to test the Repo only, not loading components or services
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SubRedditRepoTest {

    @Autowired SubredditRepository repositoryReal;

    @Test
    public void testAddSubredditSuccess() {
        Subreddit entity = new Subreddit();
        entity.setDescription("Desc in southampton");
        entity.setName("Name in southampton");

        Subreddit entityResult = repositoryReal.save(entity);

        assertThat(entityResult).isNotNull();
        assertThat(entityResult.getDescription()).isEqualTo("Desc in southampton");

    }

}
