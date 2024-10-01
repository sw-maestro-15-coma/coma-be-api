package com.swmaestro.cotuber.video.dto;

import lombok.Builder;

@Builder
public record VideoSubtitleGenerateMessageRequest(
        long videoId,
        String s3Url
) {}
