package com.swmaestro.cotuber.video.dto;

import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import lombok.Builder;

import java.util.List;

@Builder
public record VideoSubtitleGenerateMessageResponse(
        long videoId,
        List<VideoSubtitle> subtitleList
) {}
