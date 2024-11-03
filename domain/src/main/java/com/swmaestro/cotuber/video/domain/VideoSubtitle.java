package com.swmaestro.cotuber.video.domain;

import com.swmaestro.cotuber.video.dto.VideoSubtitleBaseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class VideoSubtitle {
    private final long id;
    private final long videoId;
    private String subtitle;
    private int start;
    private int end;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static VideoSubtitle from(Video video, VideoSubtitleBaseDto subtitleBase) {
        return VideoSubtitle.builder()
                .videoId(video.getId())
                .subtitle(subtitleBase.subtitle())
                .start(subtitleBase.start())
                .end(subtitleBase.end())
                .build();
    }
}
