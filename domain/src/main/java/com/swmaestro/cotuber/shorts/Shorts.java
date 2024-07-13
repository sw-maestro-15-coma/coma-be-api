package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.video.ProgressState;
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
    private String link;
    private String topTitle;
    private ProgressState progressState;

    @Builder
    public Shorts(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                  long userId, long videoId,
                  String link, String topTitle, ProgressState progressState) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.videoId = videoId;
        this.link = link;
        this.topTitle = topTitle;
        this.progressState = progressState;
    }
}
