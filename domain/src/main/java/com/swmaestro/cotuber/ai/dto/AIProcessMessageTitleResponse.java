package com.swmaestro.cotuber.ai.dto;

import lombok.Builder;

@Builder
public record AIProcessMessageTitleResponse(long userVideoRelationId,
                                            String title) {
}
