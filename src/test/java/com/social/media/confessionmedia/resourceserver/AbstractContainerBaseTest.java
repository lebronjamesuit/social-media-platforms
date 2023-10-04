package com.social.media.confessionmedia.resourceserver;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;


public abstract class AbstractContainerBaseTest {

    public static final PostgreSQLContainer postgresqlContainer ;

    static {
        postgresqlContainer = new PostgreSQLContainer("postgres:15")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");
        postgresqlContainer.start();
    }
}


