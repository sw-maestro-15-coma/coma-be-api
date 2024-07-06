package com.swmaestro.cotuber.domain.video;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Video {
    private long id;
    private String youtubeUrl;
    private String s3Path;

    @Builder
    public Video(long id, String youtubeUrl, String s3Path) {
        this.id = id;
        this.youtubeUrl = youtubeUrl;
        this.s3Path = s3Path;
    }
}
