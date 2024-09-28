package com.swmaestro.cotuber.userVideoRelation.domain;

public enum UserVideoRelationStatus {
    VIDEO_DOWNLOADING("video_downloading"),
    AI_PROCESSING("ai_processing"),
    COMPLETE("complete"),
    ERROR("error");

    private final String state;

    UserVideoRelationStatus(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
