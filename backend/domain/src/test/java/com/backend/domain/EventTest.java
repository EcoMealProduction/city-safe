package com.backend.domain;

import com.backend.happening.Event;
import com.backend.shared.Location;
import com.backend.user.Comment;
import com.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private Event event;

    @BeforeEach
    void setup() {
        event = Event.builder()
                .id(UUID.randomUUID())
                .title("Event de test")
                .description("Acesta este un test pentru event.")
                .user(User.builder().
                        id(UUID.randomUUID())
                        .username("Calin")
                        .build())
                .location(Location.builder()
                        .latitude(BigDecimal.valueOf(46.0))
                        .longitude(BigDecimal.valueOf(28.0))
                        .address("Str. Stefan cel Mare 1")
                        .build())
                .comments(List.of())
                .likes(0)
                .dislikes(0)
                .startTime(LocalDateTime.now().plusMinutes(1))
                .endTime(LocalDateTime.now().plusMinutes(31))
                .build();
    }

    // -------------------------------
    // Testări creare validă
    // -------------------------------

    @Test
    void testValidEventIsCreatedSuccessfully() {
        assertEquals("Event de test", event.title());
        assertEquals("Acesta este un test pentru event.", event.description());
        assertNotNull(event.id());
        assertNotNull(event.user());
        assertNotNull(event.location());
        assertNotNull(event.startTime());
        assertNotNull(event.endTime());
        assertNotNull(event.comments());
        assertEquals(0, event.likes());
        assertEquals(0, event.dislikes());
    }


    // -------------------------------
    // Testari metode functionale
    // -------------------------------

    @Test
    void testAddLike() {
        event = (Event) event.addLike();
        assertEquals(1, event.likes());
    }

    @Test
    void testRemoveLike() {
        event = (Event) event.addLike();
        event = (Event) event.removeLike();
        assertEquals(0, event.likes());
    }

    @Test
    void testAddDislike() {
        event = (Event) event.addDislike();
        assertEquals(1, event.dislikes());
    }

    @Test
    void testRemoveDislike() {
        event = (Event) event.addDislike();
        event = (Event) event.removeDislike();
        assertEquals(0, event.dislikes());
    }

    @Test
    void testAddComment() {
        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .happeningId(event.id())
                .user(event.user())
                .text("Super event de test.")
                .createdAt(LocalDateTime.now())
                .build();

        event = (Event) event.addComment(comment);
        assertEquals(1, event.comments().size());
        assertEquals("Super event de test.", event.comments().getFirst().text());
    }

    @Test
    void testEventIsFinished() {
        LocalDateTime now = LocalDateTime.now().plusMinutes(2); // trebuie să fie în viitor
        LocalDateTime start = now.plusMinutes(1);
        LocalDateTime end = start.plusMinutes(30);

        Event testEvent = event.toBuilder()
                .startTime(start)
                .endTime(end)
                .build();

        // După endTime -> trebuie să fie finalizat
        assertTrue(testEvent.isFinished(end.plusMinutes(1)));

        // În timpul evenimentului -> nu e finalizat
        assertFalse(testEvent.isFinished(start.plusMinutes(15)));
    }


    // -------------------------------
    // Testări validări constructor
    // -------------------------------

    @Test
    void testShortTitleThrowsException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .title("Scurt")
                .build());

        assertEquals("Title too short.", exception.getMessage());
    }

    @Test
    void testShortDescriptionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .description("Scurt")
                .build());

        assertEquals("Description too short.", exception.getMessage());
    }

    @Test
    void testNegativeLikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .likes(-1)
                .build());

        assertEquals("Likes and dislikes cannot be negative.", exception.getMessage());
    }

    @Test
    void testNegativeDislikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .dislikes(-1)
                .build());

        assertEquals("Likes and dislikes cannot be negative.", exception.getMessage());
    }

    @Test
    void testNegativeLikesAndDislikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .likes(-1)
                .dislikes(-1)
                .build());
        assertEquals("Likes and dislikes cannot be negative.", exception.getMessage());
    }

    @Test
    void testStartTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .startTime(LocalDateTime.now().minusMinutes(5))
                .build());

        assertEquals("Start and end time cannot be before now.", exception.getMessage());
    }

    @Test
    void testEndTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .endTime(LocalDateTime.now().minusMinutes(5))
                .build());

        assertEquals("Start and end time cannot be before now.", exception.getMessage());
    }

    @Test
    void testTooShortDurationThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.toBuilder()
                .startTime(LocalDateTime.now().plusMinutes(1))
                .endTime(LocalDateTime.now().plusMinutes(5))
                .build());

        assertEquals("Event duration must be at least 30 minutes.", exception.getMessage());
    }

    @Test
    void testNullCommentsReplacedWithEmptyList() {
        Event testEvent = event.toBuilder()
                .comments(null)
                .build();

        assertNotNull(testEvent.comments());
        assertTrue(testEvent.comments().isEmpty());
    }
}
