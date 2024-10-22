package com.swmaestro.cotuber.shorts.dto;

import com.swmaestro.cotuber.draft.dto.SubtitleDto;
import lombok.Builder;

import java.util.List;

@Builder
public record ShortsGenerateMessageRequest(
        long shortsId,
        String topTitle,
        String videoS3Url,
        int startTime,
        int endTime,
        List<SubtitleDto> subtitleList
) {}
