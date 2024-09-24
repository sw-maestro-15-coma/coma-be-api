package com.swmaestro.cotuber.shorts.dto;

import lombok.Builder;

@Builder
public record ShortsProcessMessageRequest(
        long videoId,
        long shortsId,
        String topTitle,
        String videoS3Url,
        String startTime,
        String endTime
) {}
