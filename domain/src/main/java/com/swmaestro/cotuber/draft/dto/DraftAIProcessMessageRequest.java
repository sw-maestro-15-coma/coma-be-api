package com.swmaestro.cotuber.draft.dto;

import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import lombok.Builder;

import java.util.List;

@Builder
public record DraftAIProcessMessageRequest(
        long draftId,
        List<SubtitleDto> subtitleList
) {
    public static DraftAIProcessMessageRequest from(long draftId, final List<VideoSubtitle> videoSubtitles) {
        return new DraftAIProcessMessageRequest(
                draftId,
                videoSubtitles.stream()
                        .map(vsl -> SubtitleDto.builder()
                                .start(vsl.getStart())
                                .end(vsl.getEnd())
                                .subtitle(vsl.getSubtitle())
                                .build()
                        )
                        .toList()
        );
    }
}
