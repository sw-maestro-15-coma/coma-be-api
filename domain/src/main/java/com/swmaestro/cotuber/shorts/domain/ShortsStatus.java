package com.swmaestro.cotuber.shorts.domain;

public enum ShortsStatus {
    GENERATING("shorts_generating"),
    COMPLETE("complete"),
    ERROR("error");

    private final String state;

    ShortsStatus(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
