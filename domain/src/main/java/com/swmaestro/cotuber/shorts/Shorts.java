package com.swmaestro.cotuber.shorts;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.shorts.ProgressState.ERROR;
import static com.swmaestro.cotuber.shorts.ProgressState.YOUTUBE_DOWNLOADING;

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
    private String thumbnailUrl;

    @Builder
    public Shorts(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                  long userId, long videoId, String link,
                  String topTitle, ProgressState progressState, String thumbnailUrl) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.videoId = videoId;
        this.link = link;
        this.topTitle = topTitle;
        this.progressState = progressState;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static Shorts initialShorts(final long userId, final long videoId, final String topTitle) {
        return Shorts.builder()
                .userId(userId)
                .videoId(videoId)
                .topTitle(topTitle)
                .progressState(YOUTUBE_DOWNLOADING)
                .build();
    }

    public void changeProgressState(final ProgressState state) {
        progressState = state;
    }

    public void changeStateError() {
        progressState = ERROR;
    }

    public void changeLink(final String link) {
        this.link = link;
    }
}
