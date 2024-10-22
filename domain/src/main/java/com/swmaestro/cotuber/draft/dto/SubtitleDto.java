package com.swmaestro.cotuber.draft.dto;

import lombok.Builder;

@Builder
public record SubtitleDto(int start, int end, String subtitle) {

}
