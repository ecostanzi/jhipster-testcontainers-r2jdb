package com.mycompany.myapp.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseTestcontainersExtension implements BeforeAllCallback {

    private static AtomicBoolean started = new AtomicBoolean(false);

    private static PostgreSQLContainer container;

    @DynamicPropertySource
    public static void r2dbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> container.getJdbcUrl().replace("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", () -> container.getUsername());
        registry.add("spring.r2dbc.password", () -> container.getPassword());

        registry.add("spring.liquibase.url", () -> container.getJdbcUrl());
        registry.add("spring.liquibase.user", () -> container.getUsername());
        registry.add("spring.liquibase.password", () -> container.getPassword());
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

        if(!started.get()) {
            PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.1")
                .withDatabaseName("reacttestcnt");
            container.start();

//            System.setProperty("spring.r2dbc.url", container.getJdbcUrl().replace("jdbc", "r2dbc"));
//            System.setProperty("spring.r2dbc.username", container.getUsername());
//            System.setProperty("spring.r2dbc.password", container.getPassword());
//            System.setProperty("spring.liquibase.url", container.getJdbcUrl());
//            System.setProperty("spring.liquibase.user", container.getUsername());
//            System.setProperty("spring.liquibase.password", container.getPassword());

            started.set(true);

        }

    }
}
