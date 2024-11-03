package com.swmaestro.cotuber.draft.dto;

import com.swmaestro.cotuber.edit.domain.EditSubtitle;
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
}
