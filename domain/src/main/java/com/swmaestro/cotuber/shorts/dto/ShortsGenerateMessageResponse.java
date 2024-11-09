package com.swmaestro.cotuber.shorts.dto;

public record ShortsGenerateMessageResponse(
        long shortsId,
        String s3Url,
        String thumbnailUrl
) {
}
