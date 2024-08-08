package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.StringUtil.convertToUTF8;

@Getter
public class Video {
    private long id;
    private String title;
    private String s3Url;
    private String youtubeUrl;
    private int videoTotalSecond;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Video(
            long id,
            String title,
            String s3Url,
            String youtubeUrl,
            int videoTotalSecond,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.s3Url = s3Url;
        this.youtubeUrl = youtubeUrl;
        this.videoTotalSecond = videoTotalSecond;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Video initialVideo(final VideoCreateRequestDto request) {
        return Video.builder()
                .id(0)
                .s3Url(null)
                .title("제목을 받아오는 중입니다...")
                .youtubeUrl(request.youtubeUrl())
                .build();
    }

    public void updateVideoInfo(VideoDownloadMessageResponse response) {
        this.s3Url = response.s3Url();
        this.videoTotalSecond = response.videoFullSecond();
        this.title = convertToUTF8(response.originalTitle());
    }
}
