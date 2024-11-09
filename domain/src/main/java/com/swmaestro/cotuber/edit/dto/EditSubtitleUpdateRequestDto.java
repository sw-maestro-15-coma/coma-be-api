package com.swmaestro.cotuber.edit.dto;

import java.util.List;

public record EditSubtitleUpdateRequestDto(
        List<EditSubtitleBaseDto> subtitleList
) {
}
