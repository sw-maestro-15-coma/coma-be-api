package com.swmaestro.cotuber.video.domain;

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
}
