package com.swmaestro.cotuber.shorts;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Shorts {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long userId;
    private long videoId;
    private String s3Path;
    private String topTitle;

    @Builder
    public Shorts(long id, LocalDateTime createdAt, LocalDateTime updatedAt, long userId, long videoId, String s3Path, String topTitle) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.videoId = videoId;
        this.s3Path = s3Path;
        this.topTitle = topTitle;
    }
}
