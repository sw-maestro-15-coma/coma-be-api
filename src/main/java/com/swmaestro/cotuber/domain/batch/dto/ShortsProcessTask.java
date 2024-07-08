package com.swmaestro.cotuber.domain.batch.dto;

import lombok.Builder;

@Builder
public record ShortsProcessTask(String s3Url, String title, String start, String end) {
}
