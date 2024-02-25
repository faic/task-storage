package org.taskstorage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("yourdatabase")
            .withUsername("youruser")
            .withPassword("yourpassword");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.datasource.url=" + mysql.getJdbcUrl(),
                    "spring.datasource.username=" + mysql.getUsername(),
                    "spring.datasource.password=" + mysql.getPassword(),
                    "spring.flyway.enabled=true"
            );
            values.applyTo(context.getEnvironment());
        }
    }
}
