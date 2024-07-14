package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Video {
    private long id;
    private String title;
    private String s3Url;
    private String youtubeUrl;
    private int length;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Video(long id, String title, String s3Url, String youtubeUrl, int length, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.s3Url = s3Url;
        this.youtubeUrl = youtubeUrl;
        this.length = length;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Video initialVideo(final VideoCreateRequestDto request) {
        return Video.builder()
                .s3Url("")
                .title("제목을 받아오는 중입니다...")
                .youtubeUrl(request.url())
                .build();
    }

    public void changeTitle(final String title) {
        this.title = title;
    }

    public void changeS3Url(final String s3Url) {
        this.s3Url = s3Url;
    }

    public void changeLength(final int length) {
        this.length = length;
    }
}
