package com.backend.in.dto.response;

import com.backend.in.dto.shared.CommentDto;
import com.backend.in.dto.shared.LocationDto;
import com.backend.in.dto.shared.SentimentEngagementDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record IncidentResponseDto(
        String title,
        String description,
        String authorUsername,
        LocalDateTime createdAt,
        LocationDto location,
        SentimentEngagementDto sentiment,
        List<CommentDto> comments

) {}
