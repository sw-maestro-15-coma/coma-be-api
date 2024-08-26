package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.StringUtil.convertToUTF8;

@Getter
public class Video {
    private static final String INITIAL_TITLE = "제목을 받아오는 중입니다...";
    private final long id;
    private String title;
    private String s3Url;
    private final YoutubeUrl youtubeUrl;
    private int videoTotalSecond;
    private final LocalDateTime createdAt;
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
        this.youtubeUrl = new YoutubeUrl(youtubeUrl);
        this.videoTotalSecond = videoTotalSecond;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Video initialVideo(final VideoCreateRequestDto request) {
        return Video.builder()
                .id(0)
                .title(INITIAL_TITLE)
                .youtubeUrl(request.youtubeUrl())
                .s3Url(null)
                .build();
    }

    public void updateVideoInfo(final VideoDownloadMessageResponse response) {
        this.s3Url = response.s3Url();
        this.videoTotalSecond = response.videoFullSecond();
        this.title = convertToUTF8(response.originalTitle());
    }

    public String getYoutubeUrlString() {
        return this.youtubeUrl.getUrl();
    }
}
