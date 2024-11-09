package com.swmaestro.cotuber.video.domain;

public enum VideoStatus {
    VIDEO_DOWNLOADING("video_downloading"),
    SUBTITLE_GENERATING("subtitle_generating"),
    COMPLETE("complete"),
    DOWNLOAD_ERROR("download_error"),
    SUBTITLE_GENERATE_ERROR("subtitle_generate_error"),
    ;

    private final String state;

    VideoStatus(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
