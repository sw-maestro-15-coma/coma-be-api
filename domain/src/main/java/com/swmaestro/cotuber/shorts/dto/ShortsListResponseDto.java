package com.swmaestro.cotuber.shorts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.shorts.Shorts;
import lombok.Builder;

@Builder
public record ShortsListResponseDto(
        long id,
        @JsonProperty("s3_url") String s3Url,
        @JsonProperty("thumbnail_url") String thumbnailUrl,
        @JsonProperty("top_title") String topTitle
) {
    public ShortsListResponseDto(Shorts shorts) {
        this(
                shorts.getId(),
                shorts.getLink(),
                shorts.getThumbnailUrl(),
                shorts.getTopTitle()
        );
    }
}
