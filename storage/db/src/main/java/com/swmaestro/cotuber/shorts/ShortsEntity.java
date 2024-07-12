package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.user.UserEntity;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "shorts")
public class ShortsEntity extends BaseEntity {
    @Column(name = "user_id")
    private long userId;

    @Column(name = "video_id")
    private long videoId;

    @Column(name = "top_title", length = 100)
    private String topTitle;

    @Column(name = "s3_path")
    private String s3Path;

    @Builder
    public ShortsEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt, long userId, long videoId, String topTitle, String s3Path) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.videoId = videoId;
        this.topTitle = topTitle;
        this.s3Path = s3Path;
    }

    public Shorts toDomain() {
        return Shorts.builder()
                .id(getId())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .userId(userId)
                .videoId(videoId)
                .topTitle(topTitle)
                .s3Path(s3Path)
                .build();
    }

    public static ShortsEntity from(final Shorts shorts) {
        return ShortsEntity.builder()
                .id(shorts.getId())
                .userId(shorts.getUserId())
                .videoId(shorts.getVideoId())
                .createdAt(shorts.getCreatedAt())
                .updatedAt(shorts.getUpdatedAt())
                .topTitle(shorts.getTopTitle())
                .s3Path(shorts.getS3Path())
                .build();
    }
}
