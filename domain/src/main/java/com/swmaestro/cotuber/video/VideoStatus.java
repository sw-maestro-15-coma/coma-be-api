package com.swmaestro.cotuber.video;

public enum VideoStatus {
    VIDEO_DOWNLOADING("video_downloading"),
    COMPLETE("complete"),
    ERROR("error");

    private final String state;

    VideoStatus(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
