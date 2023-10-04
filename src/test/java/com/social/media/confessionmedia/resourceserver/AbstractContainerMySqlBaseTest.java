package com.social.media.confessionmedia.resourceserver;

import org.testcontainers.containers.MySQLContainer;

public class AbstractContainerMySqlBaseTest {

    public static  MySQLContainer mySQLContainer ;

    static {
        mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
                .withDatabaseName("test-db")
                .withUsername("testuser")
                .withPassword("pass")
                .withReuse(true);
        mySQLContainer.start();
    }
}
