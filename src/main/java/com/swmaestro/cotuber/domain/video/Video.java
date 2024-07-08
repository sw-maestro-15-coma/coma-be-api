package com.swmaestro.cotuber.domain.video;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Video {
    private final long id;
    private final String youtubeUrl;
    private String s3Path;

    @Builder
    Video(long id, String youtubeUrl, String s3Path) {
        this.id = id;
        this.youtubeUrl = youtubeUrl;
        this.s3Path = s3Path;
    }

    public void updateS3Path(final String s3Path) {
        this.s3Path = s3Path;
    }
}
