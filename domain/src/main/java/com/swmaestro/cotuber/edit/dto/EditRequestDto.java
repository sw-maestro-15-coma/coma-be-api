package com.swmaestro.cotuber.edit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EditRequestDto(
        @JsonProperty("title") String title,
        @JsonProperty("start") int start,
        @JsonProperty("end") int end
) {}
