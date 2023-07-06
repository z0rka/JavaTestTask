package com.alibou.security.service;

import com.alibou.security.model.User;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;


/**
 * @author ururu 28.03.2023
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private static final String DB_NAME = "test";

    private static final String USER = "postgres";

    private static final String PWD = "postgres";

    @ClassRule
    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.6")
            .withDatabaseName(DB_NAME)
            .withUsername(USER)
            .withPassword(PWD);

    @Autowired
    private UserService userService;
    

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void deleteUserTest() {
        User userDto = new User(1, "test", "test",null,null);
        userService.addUser(userDto);
        userService.deleteUser(1);

        try {
            userService.getUserById(1);
            Assertions.fail();
        } catch (RuntimeException e) {
            Assertions.assertEquals("User not found", e.getMessage());
        }
    }
}
