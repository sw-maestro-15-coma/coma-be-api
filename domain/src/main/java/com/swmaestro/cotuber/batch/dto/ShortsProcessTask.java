package com.swmaestro.cotuber.batch.dto;

import lombok.Builder;

@Builder
public record ShortsProcessTask(
        long userId,
        String s3Url,
        String topTitle,
        int start,
        int end,
        long shortsId,
        long editPointId
) {
}
