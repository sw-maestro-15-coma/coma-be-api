package com.swmaestro.cotuber.edit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import lombok.Builder;

import java.util.List;

@Builder
public record EditResponseDto(
        @JsonProperty("title") String title,
        @JsonProperty("start") int start,
        @JsonProperty("end") int end,
        @JsonProperty("subtitleList") List<EditSubtitleBaseDto> subtitleList
) {
    public EditResponseDto(Edit edit, List<EditSubtitle> editSubtitleList) {
        this(
                edit.getTitle(),
                edit.getStart(),
                edit.getEnd(),
                editSubtitleList.stream().map(EditSubtitleBaseDto::new).toList()
        );
    }
}