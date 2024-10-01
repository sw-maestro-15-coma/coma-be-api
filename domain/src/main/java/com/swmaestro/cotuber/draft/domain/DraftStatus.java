package com.swmaestro.cotuber.draft.domain;

public enum DraftStatus {
    VIDEO_DOWNLOADING("video_downloading"),
    VIDEO_SUBTITLE_GENERATING("video_subtitle_generating"),
    AI_PROCESSING("ai_processing"),
    COMPLETE("complete"),
    ERROR("error");

    private final String state;

    DraftStatus(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
