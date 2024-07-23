package com.swmaestro.cotuber.shorts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ShortsListResponseDto(long id, @JsonProperty("s3_url") String s3Url,
                                    @JsonProperty("thumbnail_url") String thumbnailUrl,
                                    @JsonProperty("top_title") String topTitle) {
}
