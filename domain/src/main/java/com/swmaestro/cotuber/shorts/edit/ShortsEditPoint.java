package com.swmaestro.cotuber.shorts.edit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public static ShortsEditPoint initialEditPoint(long shortsId, long videoId) {
        return ShortsEditPoint.builder()
                .shortsId(shortsId)
                .videoId(videoId)
                .build();
    }

    public void calculateDuration(final int videoLength, final int popularPointSeconds) {
        start = calculateStart(popularPointSeconds);
        end = calculateEnd(videoLength, popularPointSeconds);
    }

    public String getFormattedStart() {
        return secondToFormat(start);
    }

    public String getFormattedEnd() {
        return secondToFormat(end);
    }

    private int calculateStart(final int popularPointSeconds) {
        final int desiredStart = popularPointSeconds - UNIT;

        if (isMinus(desiredStart)) {
            return 0;
        }
        return desiredStart;
    }

    private boolean isMinus(final int second) {
        return (second < 0);
    }

    private int calculateEnd(final int videoLength, final int popularPointSeconds) {
        final int desired = popularPointSeconds + UNIT;

        if (isOver(videoLength, desired)) {
            return videoLength;
        }

        return desired;
    }

    private boolean isOver(final int length, final int desired) {
        return (length < desired);
    }

    private String secondToFormat(final int totalSecond) {
        final int hour = totalSecond / 3600;
        final int minute = (totalSecond % 3600) / 60;
        final int second = totalSecond % 60;

        return addZeroIfOneDigit(hour) +
                ":" +
                addZeroIfOneDigit(minute) +
                ":" +
                addZeroIfOneDigit(second);
    }

    private String addZeroIfOneDigit(int num) {
        final String digit = String.valueOf(num);
        if (digit.length() == 1) {
            return "0" + digit;
        }
        return digit;
    }
}
