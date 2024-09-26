package com.swmaestro.cotuber.ai.dto;

import lombok.Builder;

@Builder
public record AIProcessMessageSubtitleResponse(long userVideoRelationId,
                                               List<Subtitle> subtitle) {
}
