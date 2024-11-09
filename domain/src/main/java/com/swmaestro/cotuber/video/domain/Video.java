package com.swmaestro.cotuber.video.domain;

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
    private final YoutubeKey youtubeKey;
    private int videoTotalSecond;
    private VideoStatus videoStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public Video(
            long id,
            String title,
            String s3Url,
            String youtubeUrl,
            int videoTotalSecond,
            VideoStatus videoStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.s3Url = s3Url;
        this.youtubeKey = new YoutubeKey(youtubeUrl);
        this.videoTotalSecond = videoTotalSecond;
        this.videoStatus = videoStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changeVideoStatus(VideoStatus newStatus) {
        this.videoStatus = newStatus;
    }

    public void completeVideoDownloading(final VideoDownloadMessageResponse response) {
        this.s3Url = response.s3Url();
        this.videoTotalSecond = response.videoFullSecond();
        this.title = convertToUTF8(response.originalTitle());
        this.videoStatus = VideoStatus.SUBTITLE_GENERATING;
    }

    public void errorVideoDownloading() {
        this.videoStatus = VideoStatus.DOWNLOAD_ERROR;
    }

    public void completeSubtitleGenerate() {
        this.videoStatus = VideoStatus.COMPLETE;
    }

    public void errorSubtitleGenerate() {
        this.videoStatus = VideoStatus.SUBTITLE_GENERATE_ERROR;
    }

    public String getYoutubeUrl() {
        return this.youtubeKey.getUrl();
    }
}
