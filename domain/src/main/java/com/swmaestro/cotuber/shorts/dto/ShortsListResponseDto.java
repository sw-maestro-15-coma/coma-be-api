package com.swmaestro.cotuber.shorts.dto;

import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.domain.ShortsStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ShortsListResponseDto(
        long id,
        String title,
        String s3Url,
        String thumbnailUrl,
        ShortsStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ShortsListResponseDto(Shorts shorts) {
        this(
                shorts.getId(),
                shorts.getTitle(),
                shorts.getS3Url(),
                shorts.getThumbnailUrl(),
                shorts.getShortsStatus(),
                shorts.getCreatedAt(),
                shorts.getUpdatedAt()
        );
    }
}
