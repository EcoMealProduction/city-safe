package com.backend.in.dto.shared;

import lombok.Builder;

@Builder(toBuilder = true)
public record SentimentEngagementDto(
        int likes,
        int dislikes
) {}
