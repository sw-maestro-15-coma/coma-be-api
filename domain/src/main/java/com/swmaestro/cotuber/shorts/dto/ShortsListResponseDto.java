package com.swmaestro.cotuber.shorts.dto;

import lombok.Builder;

@Builder
public record ShortsListResponseDto(long id, String link) {
}
