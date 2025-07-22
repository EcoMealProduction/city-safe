package com.backend.in.dto.response;

import com.backend.in.dto.shared.CommentDto;
import com.backend.in.dto.shared.LocationDto;
import com.backend.in.dto.shared.SentimentEngagementDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record EventResponseDto(
        String title,
        String description,
        String authorUsername,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocationDto location,
        SentimentEngagementDto sentiment,
        List<CommentDto> comments
) {}
