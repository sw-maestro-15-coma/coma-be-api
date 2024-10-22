package com.swmaestro.cotuber.edit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import lombok.Builder;

@Builder
public record EditSubtitleBaseDto(
        @JsonProperty("id") long id,
        @JsonProperty("subtitle") String subtitle,
        @JsonProperty("start") int start,
        @JsonProperty("end") int end
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
