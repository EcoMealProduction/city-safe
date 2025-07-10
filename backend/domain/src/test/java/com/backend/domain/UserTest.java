package com.backend.domain;

import com.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID())
                .username("Dorin")
                .build();
    }

    // -------------------------------
    // Testari creare valida
    // -------------------------------

    @Test
    void testValidUserIsCreatedSuccessfully() {
        assertNotNull(user.id());
        assertEquals("Dorin", user.username());
    }

    // -------------------------------
    // Testări validări constructor
    // -------------------------------

    @Test
    void testUsernameTooShortThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.toBuilder()
                .username("Yo")
                .build());

        assertEquals("Username must be at least 3 characters.", exception.getMessage());
    }

    @Test
    void testUsernameEmptyThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.toBuilder()
                .username(" ")
                .build());
        assertEquals("Username cannot be empty or only spaces.", exception.getMessage());
    }

    @Test
    void testInvalidUsernameFormatThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.toBuilder()
                .username("#Dorin$")
                .build());
        assertEquals("Invalid username format.", exception.getMessage());
    }
}
