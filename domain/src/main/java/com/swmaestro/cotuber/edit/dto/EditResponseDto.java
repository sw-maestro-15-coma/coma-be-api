package com.swmaestro.cotuber.edit.dto;

import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public record EditResponseDto(
        String title,
        int start,
        int end,
        List<EditSubtitleBaseDto> subtitleList
) {
    public EditResponseDto(Edit edit, List<EditSubtitle> editSubtitleList) {
        this(
                edit.getTitle(),
                edit.getStart(),
                edit.getEnd(),
                editSubtitleList.stream().map(EditSubtitleBaseDto::new).toList()
        );
    }

    public EditResponseDto() {
        this(null, 0, 0, Collections.emptyList());
    }
}