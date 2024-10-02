package com.swmaestro.cotuber.video.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import lombok.Builder;

@Builder
public record VideoSubtitleBaseDto(
        @JsonProperty("subtitle") String subtitle,
        @JsonProperty("start") int start,
        @JsonProperty("end") int end
) {
    public VideoSubtitleBaseDto(VideoSubtitle videoSubtitle) {
        this(
                videoSubtitle.getSubtitle(),
                videoSubtitle.getStart(),
                videoSubtitle.getEnd()
        );
    }
}
