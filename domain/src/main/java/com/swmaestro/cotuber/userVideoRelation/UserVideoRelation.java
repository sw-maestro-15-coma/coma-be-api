package com.swmaestro.cotuber.userVideoRelation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Builder
@Getter
public class UserVideoRelation {
    private final long id;
    private final long userId;
    private final long videoId;
    private UserVideoRelationStatus userVideoRelationStatus;
    private EnumSet<UserVideoRelationCompleteStatus> userVideoRelationCompleteStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void completeVideoDownloading() {
        this.userVideoRelationStatus = UserVideoRelationStatus.AI_PROCESSING;
    }

    public void completeAiEditProcessing() {
        if (this.userVideoRelationCompleteStatus == null) {
            this.userVideoRelationCompleteStatus = EnumSet.noneOf(UserVideoRelationCompleteStatus.class);
        }
        this.userVideoRelationCompleteStatus.add(UserVideoRelationCompleteStatus.EDIT_COMPLETE);
        checkAndUpdateCompleteStatus();
    }

    public void completeTitleProcessing() {
        if (this.userVideoRelationCompleteStatus == null) {
            this.userVideoRelationCompleteStatus = EnumSet.noneOf(UserVideoRelationCompleteStatus.class);
        }
        this.userVideoRelationCompleteStatus.add(UserVideoRelationCompleteStatus.TITLE_COMPLETE);
        checkAndUpdateCompleteStatus();
    }

    public void completeSubtitleProcessing() {
        if (this.userVideoRelationCompleteStatus == null) {
            this.userVideoRelationCompleteStatus = EnumSet.noneOf(UserVideoRelationCompleteStatus.class);
        }
        this.userVideoRelationCompleteStatus.add(UserVideoRelationCompleteStatus.SUBTITLE_COMPLETE);
        checkAndUpdateCompleteStatus();
    }

    private void checkAndUpdateCompleteStatus() {
        if (userVideoRelationCompleteStatus.containsAll(EnumSet.of(
                UserVideoRelationCompleteStatus.TITLE_COMPLETE,
                UserVideoRelationCompleteStatus.SUBTITLE_COMPLETE,
                UserVideoRelationCompleteStatus.EDIT_COMPLETE))) {
            this.userVideoRelationStatus = UserVideoRelationStatus.COMPLETE;
        }
    }
}
