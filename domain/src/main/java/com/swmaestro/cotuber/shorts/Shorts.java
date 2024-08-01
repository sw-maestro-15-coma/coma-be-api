package com.swmaestro.cotuber.shorts;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.shorts.ProgressState.ERROR;
import static com.swmaestro.cotuber.shorts.ProgressState.YOUTUBE_DOWNLOADING;

@Builder
@Getter
public class Shorts {
    private final long id;
    private final long userId;
    private final long videoId;
    private String link;
    private String topTitle;
    private ProgressState progressState;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public void assignTopTitle(final String topTitle) {
        this.topTitle = topTitle;
    }

    public void assignThumbnailUrl(final String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void completeShorts(String link) {
        this.link = link;
        this.progressState = ProgressState.COMPLETE;
    }
}
