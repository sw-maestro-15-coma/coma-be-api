package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntity {
    @Column(name = "s3_url")
    private String s3Url;
    @Column(name = "youtube_url")
    private String youtubeUrl;
    @Column(name = "length")
    private int length;

    @Builder
    public VideoEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                       String s3Url, String youtubeUrl, int length) {
        super(id, createdAt, updatedAt);
        this.s3Url = s3Url;
        this.youtubeUrl = youtubeUrl;
        this.length = length;
    }

    public Video toDomain() {
        return Video.builder()
                .id(getId())
                .s3Url(s3Url)
                .youtubeUrl(youtubeUrl)
                .length(length)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static VideoEntity from(Video video) {
        return VideoEntity.builder()
                .id(video.getId())
                .s3Url(video.getS3Url())
                .youtubeUrl(video.getYoutubeUrl())
                .length(video.getLength())
                .createdAt(video.getCreatedAt())
                .updatedAt(video.getUpdatedAt())
                .build();
    }
}
