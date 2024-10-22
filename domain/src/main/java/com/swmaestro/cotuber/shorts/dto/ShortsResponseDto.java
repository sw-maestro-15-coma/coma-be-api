package com.swmaestro.cotuber.shorts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.domain.ShortsStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ShortsResponseDto(
        long id,
        @JsonProperty("s3Url") String s3Url,
        @JsonProperty("thumbnailUrl") String thumbnailUrl,
        @JsonProperty("status") ShortsStatus shortsStatus,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
) {
    public ShortsResponseDto(Shorts shorts) {
        this(
                shorts.getId(),
                shorts.getS3Url(),
                shorts.getThumbnailUrl(),
                shorts.getShortsStatus(),
                shorts.getCreatedAt(),
                shorts.getUpdatedAt()
        );
    }
}
