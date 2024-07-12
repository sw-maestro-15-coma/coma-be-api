package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.video.ProgressState.YOUTUBE_DOWNLOADING;

@Getter
public class Video {
    private long id;
    private long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String youtubeUrl;
    private String s3Path;
    private ProgressState state;

    @Builder
    public Video(long id, long userId, LocalDateTime createdAt, LocalDateTime updatedAt, String youtubeUrl, String s3Path, ProgressState state) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.youtubeUrl = youtubeUrl;
        this.s3Path = s3Path;
        this.state = state;
    }

    public static Video initialVideo(final VideoCreateRequestDto request) {
        return Video.builder()
                .s3Path("")
                .state(YOUTUBE_DOWNLOADING)
                .youtubeUrl(request.url())
                .build();
    }

    public void changeS3Path(final String s3Path) {
        this.s3Path = s3Path;
    }

    public void changeState(final ProgressState to) {
        state = to;
    }
}
