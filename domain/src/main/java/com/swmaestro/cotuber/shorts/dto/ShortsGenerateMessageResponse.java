package com.swmaestro.cotuber.shorts.dto;

import lombok.Builder;

@Builder
public record ShortsGenerateMessageResponse(
        long shortsId,
        String s3Url,
        String thumbnailUrl
) {}
