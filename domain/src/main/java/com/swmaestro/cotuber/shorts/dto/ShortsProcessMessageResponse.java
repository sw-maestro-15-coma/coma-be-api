package com.swmaestro.cotuber.shorts.dto;

import lombok.Builder;

@Builder
public record ShortsProcessMessageResponse(long videoId,
                                           long shortsId,
                                           String s3Url,
                                           String thumbnailUrl) {
}
