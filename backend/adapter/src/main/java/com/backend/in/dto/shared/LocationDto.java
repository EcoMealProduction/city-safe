package com.backend.in.dto.shared;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record LocationDto(
        @NonNull BigDecimal latitude,
        @NonNull BigDecimal longitude,
        @NonNull String address
        ) {}
