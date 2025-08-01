package com.backend.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.backend.domain.Fixtures.validComment;
import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    @Test
    void testValidComment() {
        assertEquals("testUser", validComment.authorUsername());
        assertEquals("Un comentariu valid pentru test.", validComment.text());
    }

    @Test
    void testShortTextThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validComment.toBuilder()
                        .text("Abc")
                        .build()
        );
        assertEquals("Text must have at least 5 characters.", exception.getMessage());
    }

    @Test
    void testEmptyTextThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validComment.toBuilder()
                        .text("     ")
                        .build()
        );
        assertEquals("Text cannot be empty or only spaces.", exception.getMessage());
    }

    @Test
    void testCreatedAtInFutureThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                validComment.toBuilder()
                        .createdAt(LocalDateTime.now().plusMinutes(5))
                        .build()
        );
        assertEquals("Comment date cannot be in the future.", exception.getMessage());
    }
}
