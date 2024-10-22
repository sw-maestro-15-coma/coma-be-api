package com.swmaestro.cotuber.video.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.video.domain.Video;
import lombok.Builder;

@Builder
public record VideoResponseDto(
        long id,
        @JsonProperty("title") String title,
        @JsonProperty("s3Url") String s3Url,
        @JsonProperty("youtubeUrl") String youtubeUrl
) {
    public VideoResponseDto(Video video) {
        this(video.getId(), video.getTitle(), video.getS3Url(), video.getYoutubeUrl());
    }
}
