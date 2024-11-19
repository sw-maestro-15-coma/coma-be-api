package com.swmaestro.cotuber.shorts.dto;

import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.domain.ShortsStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ShortsResponseDto(
        long id,
        String title,
        String s3Url,
        String thumbnailUrl,
        ShortsStatus shortsStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ShortsResponseDto(Shorts shorts) {
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
