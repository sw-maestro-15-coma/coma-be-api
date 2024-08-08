package com.swmaestro.cotuber.shorts;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.shorts.ProgressState.AI_PROCESSING;
import static com.swmaestro.cotuber.shorts.ProgressState.ERROR;
import static com.swmaestro.cotuber.shorts.ProgressState.SHORTS_GENERATING;
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

    public static Shorts initialShorts(final long userId, final long videoId) {
        return Shorts.builder()
                .userId(userId)
                .videoId(videoId)
                .topTitle("제목 생성중...")
                .progressState(YOUTUBE_DOWNLOADING)
                .build();
    }

    public void changeStateToAIProcessing() {
        progressState = AI_PROCESSING;
    }

    public void changeStateToShortsGenerating() {
        progressState = SHORTS_GENERATING;
    }

    public void changeStateToError() {
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
