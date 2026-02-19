package com.juan.usersapi.service;

import com.juan.usersapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void shouldCreateUserSuccessfully() {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setName("Test");
        user.setPhone("+15555555555");
        user.setPassword("123456");
        user.setTaxId("ABCD990101AAA");

        User created = userService.createUser(user);

        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotEquals("123456", created.getPassword());
    }

    @Test
    void shouldThrowErrorWhenTaxIdDuplicated() {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setName("Test");
        user.setPhone("+15555555555");
        user.setPassword("123456");
        user.setTaxId("ABCD990101AAA");

        userService.createUser(user);

        User duplicate = new User();
        duplicate.setEmail("test2@mail.com");
        duplicate.setName("Test2");
        duplicate.setPhone("+15555555556");
        duplicate.setPassword("123456");
        duplicate.setTaxId("ABCD990101AAA");

        assertThrows(RuntimeException.class, () ->
                userService.createUser(duplicate));
    }

    @Test
    void shouldLoginSuccessfully() {

        User user = new User();
        user.setEmail("login@mail.com");
        user.setName("Login");
        user.setPhone("+15555555557");
        user.setPassword("123456");
        user.setTaxId("EFGH990101AAA");

        userService.createUser(user);

        String response =
                userService.login("login@mail.com", "123456");

        assertEquals("Login successful", response);
    }
}
