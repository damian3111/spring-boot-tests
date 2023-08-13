package com.damian3111.demo;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class BaseIT {

    public static PostgreSQLContainer postgreSQLContainer;


    @BeforeAll
    public static void asd() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest").withReuse(true);
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void containerConfig(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


}
