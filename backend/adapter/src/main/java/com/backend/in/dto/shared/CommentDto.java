package com.backend.in.dto.shared;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record CommentDto(
        String authorUsername,
        String text,
        LocalDateTime createdAt
) {}
