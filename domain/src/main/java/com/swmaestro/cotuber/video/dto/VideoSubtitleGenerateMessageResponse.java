package com.swmaestro.cotuber.video.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record VideoSubtitleGenerateMessageResponse(
        long videoId,
        List<VideoSubtitleBaseDto> subtitleList
) {}
