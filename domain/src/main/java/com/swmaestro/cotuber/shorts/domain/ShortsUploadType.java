package com.swmaestro.cotuber.shorts.domain;

public enum ShortsUploadType {
    YOUTUBE("youtube"),
    INSTAGRAM("instagram");

    private final String type;

    ShortsUploadType(String type) {
        this.type = type;
    }

    public String get() {
        return type;
    }
}
