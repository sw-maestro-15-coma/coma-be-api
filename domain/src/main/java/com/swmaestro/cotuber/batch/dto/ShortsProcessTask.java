package com.swmaestro.cotuber.batch.dto;

import lombok.Builder;

@Builder
public record ShortsProcessTask(String s3Url, String topTitle,
                                String start, String end,
                                long shortsId, long editPointId) {
}
