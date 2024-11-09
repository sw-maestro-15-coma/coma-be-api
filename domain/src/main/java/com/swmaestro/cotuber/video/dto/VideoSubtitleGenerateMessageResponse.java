package com.swmaestro.cotuber.video.dto;

import java.util.List;

public record VideoSubtitleGenerateMessageResponse(
        long videoId,
        List<VideoSubtitleBaseDto> subtitleList
) {
}
