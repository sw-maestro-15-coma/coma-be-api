package com.swmaestro.cotuber.domain.video;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Video {
    private long id;
    private String youtubeUrl;
    private String s3Path;
    private ProgressState state;

    @Builder
    public Video(long id, String youtubeUrl, String s3Path, ProgressState state) {
        this.id = id;
        this.youtubeUrl = youtubeUrl;
        this.s3Path = s3Path;
        this.state = state;
    }

    public void changeS3Path(final String s3Path) {
        this.s3Path = s3Path;
    }

    public void changeState(final ProgressState to) {
        state = to;
    }
}
