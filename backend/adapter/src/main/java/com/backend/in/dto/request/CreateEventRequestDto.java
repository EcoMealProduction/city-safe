package com.backend.in.dto.request;

import com.backend.in.dto.shared.LocationDto;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record CreateEventRequestDto(
        @NonNull String title,
        @NonNull String description,
        @NonNull String authorUsername,
        @NonNull LocalDateTime startTime,
        @NonNull LocalDateTime endTime,
        @NonNull LocationDto location
        ) {}
