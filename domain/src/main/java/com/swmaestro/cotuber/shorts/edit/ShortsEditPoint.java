package com.swmaestro.cotuber.shorts.edit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.StringUtil.secondToFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ShortsEditPoint {
    private static final int UNIT = 30;

    private long id;
    private long shortsId;
    private long videoId;
    private int start;
    private int end;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ShortsEditPoint(long id, long shortsId, long videoId,
                           int start, int end,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.shortsId = shortsId;
        this.videoId = videoId;
        this.start = start;
        this.end = end;
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
                .start(calculateStart(popularPointSeconds))
                .end(calculateEnd(videoLength, popularPointSeconds))
                .build();
    }

    public String getFormattedStart() {
        return secondToFormat(start);
    }

    public String getFormattedEnd() {
        return secondToFormat(end);
    }

    private static int calculateStart(final int popularPointSeconds) {
        return Math.max(popularPointSeconds - UNIT, 0);
    }

    private static int calculateEnd(final int videoLength, final int popularPointSeconds) {
        return Math.min(popularPointSeconds + UNIT, videoLength);
    }
}
