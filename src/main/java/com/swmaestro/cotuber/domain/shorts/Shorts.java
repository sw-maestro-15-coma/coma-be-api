package com.swmaestro.cotuber.domain.shorts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Shorts {
    private final long id;
    private final long videoId;
    private final String link;

    @Builder
    public Shorts(long id, long videoId, String link) {
        this.id = id;
        this.videoId = videoId;
        this.link = link;
    }
}
