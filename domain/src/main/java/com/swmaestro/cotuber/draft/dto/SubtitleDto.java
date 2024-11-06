package com.swmaestro.cotuber.draft.dto;

import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import lombok.Builder;

@Builder
public record SubtitleDto(
        int start,
        int end,
        String subtitle
) {
    public static SubtitleDto from(EditSubtitle editSubtitle) {
        return SubtitleDto.builder()
                .start(editSubtitle.getStart())
                .end(editSubtitle.getEnd())
                .subtitle(editSubtitle.getSubtitle())
                .build();
    }

    public static SubtitleDto from(VideoSubtitle videoSubtitle) {
        return SubtitleDto.builder()
                .start(videoSubtitle.getStart())
                .end(videoSubtitle.getEnd())
                .subtitle(videoSubtitle.getSubtitle())
                .build();
    }
}
