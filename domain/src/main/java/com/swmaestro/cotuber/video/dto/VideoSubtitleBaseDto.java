package com.swmaestro.cotuber.video.dto;

import lombok.Builder;

@Builder
public record VideoSubtitleBaseDto(
        String subtitle,
        int start,
        int end
) {
}
