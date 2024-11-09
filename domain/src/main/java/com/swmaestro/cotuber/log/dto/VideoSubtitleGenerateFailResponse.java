package com.swmaestro.cotuber.log.dto;

public record VideoSubtitleGenerateFailResponse(
        long videoId,
        String message
) {
}
