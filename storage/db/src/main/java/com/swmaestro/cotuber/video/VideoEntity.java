package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.video.domain.Video;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @Builder
    public VideoEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt, String title, String s3Url, String youtubeUrl, int length) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.s3Url = s3Url;
        this.youtubeUrl = youtubeUrl;
        this.length = length;
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
                .build();
    }
}
