package com.swmaestro.cotuber.video.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record VideoSubtitleBaseDto(
        @JsonProperty("subtitle") String subtitle,
        @JsonProperty("start") int start,
        @JsonProperty("end") int end
) {}
