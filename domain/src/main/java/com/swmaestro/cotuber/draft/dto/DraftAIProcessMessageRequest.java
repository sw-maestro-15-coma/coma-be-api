package com.swmaestro.cotuber.draft.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record DraftAIProcessMessageRequest(long draftId,
                                           List<SubtitleDto> subtitleList) {
}
