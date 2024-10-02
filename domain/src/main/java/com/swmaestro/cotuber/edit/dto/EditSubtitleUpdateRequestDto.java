package com.swmaestro.cotuber.edit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EditSubtitleUpdateRequestDto(
        @JsonProperty("subtitleList") List<EditSubtitleBaseDto> subtitleList
) {}
