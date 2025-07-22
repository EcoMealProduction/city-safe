package com.backend.in.dto.request;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record AddCommentRequestDto(
        @NonNull String authorUsername,
        @NonNull String text,
        @NonNull LocalDateTime createdAt
        ) {}
