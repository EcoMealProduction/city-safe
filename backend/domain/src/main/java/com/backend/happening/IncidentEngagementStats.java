package com.backend.happening;

import lombok.Builder;

/**
 * Represents engagement statistics for an {@link Incident}, including
 * confirmation and denial counts from users.
 */
@Builder(toBuilder = true)
public record IncidentEngagementStats(
        int confirms,
        int denies,
        int consecutiveDenies) {

    /**
     * Constructs an {@code IncidentEngagementStats} record with validation.
     * Ensures that no engagement metric is negative.
     *
     * @throws IllegalArgumentException if any of the fields are negative.
     */
    public IncidentEngagementStats {
        if (confirms < 0 || denies < 0 || consecutiveDenies < 0)
            throw new IllegalArgumentException("Negative values not allowed.");
    }
}
