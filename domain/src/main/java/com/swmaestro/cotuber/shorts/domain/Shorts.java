package com.swmaestro.cotuber.shorts.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.shorts.domain.ShortsStatus.*;

@Builder
@Getter
public class Shorts {
    private final long id;
    private final long userId;
    private final long videoId;
    private String s3Url;
    private String thumbnailUrl;
    private ShortsStatus shortsStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Shorts initialShorts(final long userId, final long videoId) {
        return Shorts.builder()
                .userId(userId)
                .videoId(videoId)
                .shortsStatus(GENERATING)
                .build();
    }

    public void completeShorts(String s3Url, String thumbnailUrl) {
        this.s3Url = s3Url;
        this.thumbnailUrl = thumbnailUrl;
        this.shortsStatus = ShortsStatus.COMPLETE;
    }

    public void errorShorts() {
        this.shortsStatus = ShortsStatus.ERROR;
    }
}
