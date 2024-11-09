package com.swmaestro.cotuber.video.dto;

import com.swmaestro.cotuber.video.domain.Video;
import lombok.Builder;

@Builder
public record VideoResponseDto(
        long id,
        String title,
        String s3Url,
        String youtubeUrl
) {
    public VideoResponseDto(Video video) {
        this(video.getId(), video.getTitle(), video.getS3Url(), video.getYoutubeUrl());
    }
}
