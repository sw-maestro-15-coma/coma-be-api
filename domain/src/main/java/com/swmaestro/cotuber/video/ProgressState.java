package com.swmaestro.cotuber.video;

public enum ProgressState {
    COMPLETE("complete"),
    ERROR("error"),
    YOUTUBE_DOWNLOADING("youtube_downloading"),
    SHORTS_GENERATING("shorts_generating"),
    AI_PROCESSING("ai_processing");

    private final String state;

    ProgressState(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
