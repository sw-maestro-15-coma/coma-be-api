package com.swmaestro.cotuber.edit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EditSubtitleUpdateBaseDto(
        @JsonProperty("id") long id,
        @JsonProperty("subtitle") String subtitle,
        @JsonProperty("start") int start,
        @JsonProperty("end") int end
) {
}
