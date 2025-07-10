package com.backend.domain;

import com.backend.shared.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    private Location location;

    @BeforeEach
    void setup() {
        location = Location.builder()
                .latitude(BigDecimal.valueOf(47.0))
                .longitude(BigDecimal.valueOf(28.8))
                .address("Strada Test 123")
                .build();
    }

    // -------------------------------
    // Testari creare valida
    // -------------------------------

    @Test
    void testValidLocationIsCreatedSuccessfully() {
        assertEquals(BigDecimal.valueOf(47.0), location.latitude());
        assertEquals(BigDecimal.valueOf(28.8), location.longitude());
        assertEquals("Strada Test 123", location.address());
    }


    // -------------------------------
    // Testari creare valida
    // ------------------------------

    @Test
    void testLatitudeTooLowThrowsException() {
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> location.toBuilder()
                .latitude(BigDecimal.valueOf(45.0))
                .build());

        assertEquals("Latitude must be within Moldova.", exception.getMessage());
    }

    @Test
    void testLatitudeTooHighThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                location.toBuilder()
                        .latitude(BigDecimal.valueOf(49.0))
                        .build());

        assertEquals("Latitude must be within Moldova.", exception.getMessage());
    }

    @Test
    void testLongitudeTooLowThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                location.toBuilder().longitude(BigDecimal.valueOf(26.0)).build());

        assertEquals("Longitude must be within Moldova.", exception.getMessage());
    }

    @Test
    void testLongitudeTooHighThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                location.toBuilder().longitude(BigDecimal.valueOf(31.0)).build());

        assertEquals("Longitude must be within Moldova.", exception.getMessage());
    }

    @Test
    void testAddressTooShortThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> location.toBuilder()
                .address("Str. test")
                .build());

        assertEquals("Address must have at least 10 characters.", exception.getMessage());
    }
}
