package com.backend.happening.metadata;

import com.backend.happening.Happening;
import com.backend.happening.Incident;
import com.backend.shared.Location;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Metadata specific to an {@link Incident}, including author, location,
 * creation timestamp, and expiration deadline.
 *
 * Implements the {@link Metadata} mixin interface used across different {@link Happening} types.
 */
@Builder(toBuilder = true)
public record IncidentMetadata(
        @NonNull String authorUsername,
        @NonNull Location location,
        LocalDateTime createdAt,
        LocalDateTime expirationTime) implements Metadata {

    /**
     * Constructs an {@code IncidentMetadata} instance with defaulting and validation.
     * - Sets {@code createdAt} to {@code now()} if null.
     * - Sets {@code expirationTime} to 30 minutes after {@code createdAt} if null.
     * - Validates that {@code expirationTime} is not before {@code createdAt} or the current time.
     *
     * @throws IllegalArgumentException if:
     *   {@code expirationTime} is before {@code createdAt}
     *   {@code expirationTime} is in the past
     */
    public IncidentMetadata {
        if (createdAt == null)
            createdAt = LocalDateTime.now();

        if (expirationTime == null)
            expirationTime = createdAt.plusMinutes(30);

        if (expirationTime.isBefore(createdAt))
            throw new IllegalArgumentException("Expiration time cannot be before createdAt.");

        if (expirationTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Expiration time cannot be before now.");
    }

    /**
     * Checks if the incident has expired based on the current time.
     *
     * @return {@code true} if the expiration time is in the past.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
