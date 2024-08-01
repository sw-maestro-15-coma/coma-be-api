package com.swmaestro.cotuber.shorts.edit;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShortsEditPoint {
    private static final int UNIT = 30;

    private final long id;
    private final long shortsId;
    private final long videoId;
    private final int startSecond;
    private final int endSecond;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ShortsEditPoint(long id, long shortsId, long videoId,
                           int startSecond, int endSecond,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.shortsId = shortsId;
        this.videoId = videoId;
        this.startSecond = startSecond;
        this.endSecond = endSecond;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ShortsEditPoint of(
            long shortsId,
            long videoId,
            int videoLength,
            int popularPointSeconds
    ) {
        return ShortsEditPoint.builder()
                .shortsId(shortsId)
                .videoId(videoId)
                .startSecond(calculateStart(popularPointSeconds))
                .endSecond(calculateEnd(videoLength, popularPointSeconds))
                .build();
    }

    private static int calculateStart(final int popularPointSeconds) {
        return Math.max(popularPointSeconds - UNIT, 0);
    }

    private static int calculateEnd(final int videoLength, final int popularPointSeconds) {
        return Math.min(popularPointSeconds + UNIT, videoLength);
    }
}
