package com.swmaestro.cotuber.domain.shorts.dto;

import lombok.Builder;

@Builder
public record ShortsListResponseDto(long id, String link) {
}
