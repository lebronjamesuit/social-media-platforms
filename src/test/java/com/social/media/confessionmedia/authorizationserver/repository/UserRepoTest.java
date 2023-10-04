package com.social.media.confessionmedia.authorizationserver.repository;

import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.resourceserver.AbstractContainerBaseTest;
import com.social.media.confessionmedia.resourceserver.AbstractContainerMySqlBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest extends AbstractContainerMySqlBaseTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void simpleTest(){

        System.out.println("Minh" + mySQLContainer.getJdbcUrl()); ;

        User u = new User();
        u.setEmail("abc@gmail.com");
        u.setUserName("abc");
        u.setPassword("abcpassword");
        userRepo.save(u);

    }
}