package com.swmaestro.cotuber.userVideoRelation;

public enum UserVideoRelationCompleteStatus {
    TITLE_COMPLETE("title_complete"),
    SUBTITLE_COMPLETE("subtitle_complete"),
    EDIT_COMPLETE("edit_complete");

    private final String state;

    UserVideoRelationCompleteStatus(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
