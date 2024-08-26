package com.swmaestro.cotuber.ai.dto;

import lombok.Builder;

@Builder
public record AIProcessMessageResponse(long videoId,
                                       long shortsId,
                                       int popularPointSecond) {
}
