package com.backend.in.dto.request;

import com.backend.in.dto.shared.LocationDto;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record CreateIncidentRequestDto(
        @NonNull String title,
        @NonNull String description,
        @NonNull String authorUsername,
        @NonNull LocalDateTime createdAt,
        @NonNull LocalDateTime expirationTime,
        @NonNull LocationDto location
        ) {}
