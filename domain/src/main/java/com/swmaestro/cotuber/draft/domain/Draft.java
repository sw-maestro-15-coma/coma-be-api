package com.swmaestro.cotuber.draft.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Draft {
    private final long id;
    private final long userId;
    private final long videoId;
    private final long editId;
    private DraftStatus draftStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateDraftStatus(DraftStatus newStatus) {
        this.draftStatus = newStatus;
    }

    public boolean isSubtitleGenerating() {
        return this.draftStatus == DraftStatus.VIDEO_SUBTITLE_GENERATING;
    }
}
