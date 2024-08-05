package com.swmaestro.cotuber.video.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record VideoCreateRequestDto(@JsonProperty("youtube_url") String url) {
    public void validateYoutubeUrl() {
        Objects.requireNonNull(url, "youtube url이 비어있습니다");
    }
}
