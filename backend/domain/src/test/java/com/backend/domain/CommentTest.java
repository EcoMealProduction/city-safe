package com.backend.domain;

import com.backend.user.Comment;
import com.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {
    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
                .id(UUID.randomUUID())
                .happeningId(UUID.randomUUID())
                .user(User.builder().id(UUID.randomUUID())
                        .username("Dan")
                        .build())
                .text("Text valid pentru comentariu.")
                .createdAt(LocalDateTime.now())
                .build();
    }
    // -------------------------------
    // Testari creare valida
    // -------------------------------

    @Test
    void testValidCommentIsCreatedSuccessfully() {
        assertEquals("Dan", comment.user().username());
        assertEquals("Text valid pentru comentariu.", comment.text());
        assertNotNull(comment.id());
        assertNotNull(comment.happeningId());
        assertNotNull(comment.user().id());
    }


    // -------------------------------
    // Testari validari constructor
    // -------------------------------

    @Test
    void testShortTextThrowsException() {
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> comment.toBuilder()
                .text("text")
                .build());

        assertEquals("Text must have at least 5 characters.", exception.getMessage());
    }

    @Test
    void testEmptyTextThrowsException() {
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> comment.toBuilder()
                .text("  ")
                .build());

        assertEquals("Text cannot be empty or only spaces.", exception.getMessage());
    }

    @Test
    void testCreatedAtInFutureThrowsException() {
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> comment.toBuilder()
                .createdAt(LocalDateTime.now().plusMinutes(5))
                .build());

        assertEquals("Comment date cannot be in the future.", exception.getMessage());
    }
}
