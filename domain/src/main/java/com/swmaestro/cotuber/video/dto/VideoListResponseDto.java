package com.swmaestro.cotuber.video.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.shorts.ProgressState;
import lombok.Builder;

@Builder
public record VideoListResponseDto(long id, String title, @JsonProperty("youtube_url") String youtubeUrl, ProgressState progress) {
}
