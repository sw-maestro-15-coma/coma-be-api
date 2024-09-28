package com.swmaestro.cotuber.shorts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.domain.ShortsStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ShortsListResponseDto(
        long id,
        @JsonProperty("s3_url") String s3Url,
        @JsonProperty("thumbnail_url") String thumbnailUrl,
        @JsonProperty("status") ShortsStatus status,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
    public ShortsListResponseDto(Shorts shorts) {
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
