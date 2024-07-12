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
    @Column(name = "user_id")
    private long userId;

    @Column(name = "s3_path", length = 100)
    private String s3Path;

    @Column(name = "youtube_url", length = 100, unique = true)
    private String youtubeUrl;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private ProgressState state;

    @Builder
    public VideoEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                       String s3Path, String youtubeUrl, ProgressState state) {
        super(id, createdAt, updatedAt);
        this.s3Path = s3Path;
        this.youtubeUrl = youtubeUrl;
        this.state = state;
    }

    public Video toDomain() {
        return Video.builder()
                .id(getId())
                .s3Path(s3Path)
                .youtubeUrl(youtubeUrl)
                .state(state)
                .build();
    }

    public static VideoEntity from(Video video) {
        return VideoEntity.builder()
                .id(video.getId())
                .createdAt(video.getCreatedAt())
                .updatedAt(video.getUpdatedAt())
                .s3Path(video.getS3Path())
                .youtubeUrl(video.getYoutubeUrl())
                .state(video.getState())
                .build();
    }
}
