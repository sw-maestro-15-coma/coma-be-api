package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.domain.VideoStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "s3_url")
    private String s3Url;
    @Column(name = "youtube_url")
    private String youtubeUrl;
    @Column(name = "length")
    private int length;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus;

    @Builder
    public VideoEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt, String title, String s3Url, String youtubeUrl, int length, VideoStatus videoStatus) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.s3Url = s3Url;
        this.youtubeUrl = youtubeUrl;
        this.length = length;
        this.videoStatus = videoStatus;
    }

    public Video toDomain() {
        return Video.builder()
                .id(getId())
                .title(title)
                .s3Url(s3Url)
                .youtubeUrl(youtubeUrl)
                .videoTotalSecond(length)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .videoStatus(getVideoStatus())
                .build();
    }

    public static VideoEntity from(Video video) {
        return VideoEntity.builder()
                .id(video.getId())
                .title(video.getTitle())
                .s3Url(video.getS3Url())
                .youtubeUrl(video.getYoutubeUrl())
                .length(video.getVideoTotalSecond())
                .createdAt(video.getCreatedAt())
                .updatedAt(video.getUpdatedAt())
                .videoStatus(video.getVideoStatus())
                .build();
    }
}
