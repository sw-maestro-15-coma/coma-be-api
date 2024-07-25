package com.swmaestro.cotuber.log;

public enum ProgressContext {
    YOUTUBE_DOWNLOADING("YOUTUBE_DOWNLOADING"),
    AI_PROCESSING("AI_PROCESSING"),
    SHORTS_GENERATING("SHORTS_GENERATING"),
    GPT_TITLE_GENERATING("GPT_TITLE_GENERATING")
    ;

    private final String context;

    ProgressContext(String context) {
        this.context = context;
    }
}
