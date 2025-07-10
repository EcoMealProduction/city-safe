package com.backend.domain;

import com.backend.happening.Incident;
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

public class IncidentTest {
    private Incident incident;

    @BeforeEach
    void setup() {
        incident = Incident.builder()
                .id(UUID.randomUUID())
                .title("Test Incident")
                .description("Acesta este un test pentru incidente.")
                .user(User.builder()
                        .id(UUID.randomUUID())
                        .username("Alex")
                        .build())
                .location(Location.builder()
                        .latitude(BigDecimal.valueOf(46.0))
                        .longitude(BigDecimal.valueOf(28.0))
                        .address("Str. Stefan cel Mare 1")
                        .build())
                .comments(List.of())
                .likes(0)
                .dislikes(0)
                .confirms(0)
                .denies(0)
                .consecutiveDenies(0)
                .build();
    }

    // -------------------------------
    // Testări creare validă
    // -------------------------------

    @Test
    void testValidIncidentIsCreatedSuccessfully() {
        assertEquals("Test Incident", incident.title());
        assertEquals("Acesta este un test pentru incidente.", incident.description());
        assertNotNull(incident.id());
        assertNotNull(incident.user());
        assertNotNull(incident.location());
        assertNotNull(incident.createdAt());
        assertNotNull(incident.expirationTime());
        assertNotNull(incident.comments());
        assertEquals(0, incident.likes());
        assertEquals(0, incident.dislikes());
        assertEquals(0, incident.confirms());
        assertEquals(0, incident.denies());
        assertEquals(0, incident.consecutiveDenies());
    }


    // -------------------------------
    // Testari metode functionale
    // -------------------------------

    @Test
    void testShouldBeDeletedByConsecutiveDenies() {
        incident = incident.toBuilder()
                .consecutiveDenies(3)
                .build();

        assertTrue(incident.shouldBeDeleted());
    }

    @Test
    void testShouldBeDeletedByExpiration() {
        LocalDateTime now = LocalDateTime.now();
        incident = incident.toBuilder()
                .createdAt(now.minusMinutes(10))
                .expirationTime(now.plusSeconds(1))
                .build();

        // Avansam artificial timpul doar pentru evaluare logica, nu la creare
        LocalDateTime afterExpiration = now.plusSeconds(2);

        assertTrue(incident.shouldBeDeleted(afterExpiration));
    }

    @Test
    void testShouldNotBeDeleted() {
        LocalDateTime now = LocalDateTime.now();
        incident = incident.toBuilder()
                .consecutiveDenies(1)                 // sub pragul de 3
                .expirationTime(now.plusMinutes(10)) // încă valabil
                .build();

        assertFalse(incident.shouldBeDeleted(now));
    }

    @Test
    void testIsExpiredReturnsTrue() {
        LocalDateTime now = LocalDateTime.now();
        incident = incident.toBuilder()
                .createdAt(now.minusMinutes(10))
                .expirationTime(now.plusSeconds(1))
                .build();

        LocalDateTime afterExpiration = now.plusSeconds(2);
        assertTrue(incident.isExpired(afterExpiration));
    }

    @Test
    void testIsExpiredReturnsFalse() {
        LocalDateTime now = LocalDateTime.now();
        incident = incident.toBuilder()
                .expirationTime(now.plusMinutes(5))
                .build();

        assertFalse(incident.isExpired(now));
    }

    @Test void testAddConfirm() {
        LocalDateTime initialExpiration = incident.expirationTime();

        incident = incident.toBuilder()
                .consecutiveDenies(2)
                .build();

        incident = incident.addConfirm();

        assertEquals(1, incident.confirms());
        assertEquals(0, incident.consecutiveDenies());
        assertEquals(initialExpiration.plusMinutes(5), incident.expirationTime());
    }

    @Test
    void testAddDeny() {
        incident = incident.addDeny();
        assertEquals(1, incident.denies());
        assertEquals(1, incident.consecutiveDenies());
    }

    @Test
    void testAddLike() {
        incident = (Incident) incident.addLike();
        assertEquals(1, incident.likes());
    }

    @Test
    void testRemoveLike() {
        incident = (Incident) incident.addLike();
        incident = (Incident) incident.removeLike();
        assertEquals(0, incident.likes());
    }

    @Test
    void testAddDislike() {
        incident = (Incident) incident.addDislike();
        assertEquals(1, incident.dislikes());
    }

    @Test
    void testRemoveDislike() {
        incident = (Incident) incident.addDislike();
        incident = (Incident) incident.removeDislike();
        assertEquals(0, incident.dislikes());
    }

    @Test
    void testAddComment() {
        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .happeningId(incident.id())
                .user(incident.user())
                .text("Super incident de test.")
                .createdAt(LocalDateTime.now())
                .build();

        incident = (Incident) incident.addComment(comment);
        assertEquals(1, incident.comments().size());
        assertEquals("Super incident de test.", incident.comments().getFirst().text());
    }

    // -------------------------------
    // Testari Validări constructor
    // -------------------------------

    @Test
    void testShortTitleThrowsException() {
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> incident.toBuilder()
                .title("scurt")
                .build());

        assertEquals("Title too short.", exception.getMessage());
    }

    @Test
    void testShortDescriptionThrowsException() {
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> incident.toBuilder()
                .description("scurt")
                .build());

        assertEquals("Description too short.", exception.getMessage());
    }

    @Test
    void testNegativeLikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incident.toBuilder().likes(-1).build());

        assertEquals("Negative values not allowed.", exception.getMessage());
    }

    @Test
    void testNegativeDislikesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incident.toBuilder().dislikes(-1).build());

        assertEquals("Negative values not allowed.", exception.getMessage());
    }

    @Test
    void testNegativeConfirmsThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incident.toBuilder().confirms(-1).build());

        assertEquals("Negative values not allowed.", exception.getMessage());
    }

    @Test
    void testNegativeDeniesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incident.toBuilder().denies(-1).build());

        assertEquals("Negative values not allowed.", exception.getMessage());
    }

    @Test
    void testNegativeConsecutiveDeniesThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incident.toBuilder().consecutiveDenies(-1).build());

        assertEquals("Negative values not allowed.", exception.getMessage());
    }

    @Test
    void testExpirationTimeBeforeNowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incident.toBuilder()
                        .createdAt(LocalDateTime.now().minusMinutes(5))     // înainte de expirationTime
                        .expirationTime(LocalDateTime.now().minusMinutes(1)) // în trecut
                        .build());

        assertEquals("Expiration time cannot be before now.", exception.getMessage());
    }


    @Test
    void testExpirationBeforeCreatedAtThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                incident.toBuilder()
                        .createdAt(LocalDateTime.now().plusMinutes(5))       // e în viitor
                        .expirationTime(LocalDateTime.now().plusMinutes(1)) // mai devreme decât createdAt
                        .build());

        assertEquals("Expiration time cannot be before createdAt.", exception.getMessage());
    }


    @Test
    void testNullCommentsReplacedWithEmptyList() {
        Incident testIncident = incident.toBuilder()
                .comments(null)
                .build();

        assertNotNull(testIncident.comments());
        assertTrue(testIncident.comments().isEmpty());
    }
}
