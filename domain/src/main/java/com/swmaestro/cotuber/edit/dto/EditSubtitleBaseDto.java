package com.swmaestro.cotuber.edit.dto;

import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import lombok.Builder;

@Builder
public record EditSubtitleBaseDto(
        long id,
        String subtitle,
        int start,
        int end
) {
    public EditSubtitleBaseDto(EditSubtitle editSubtitle) {
        this(
                editSubtitle.getId(),
                editSubtitle.getSubtitle(),
                editSubtitle.getStart(),
                editSubtitle.getEnd()
        );
    }
}
