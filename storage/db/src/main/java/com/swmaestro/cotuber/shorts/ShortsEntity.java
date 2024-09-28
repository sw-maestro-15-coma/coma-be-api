package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.domain.ShortsStatus;
import jakarta.persistence.*;
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

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "s3_url")
    private String s3Url;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ShortsStatus shortsStatus;

    @Builder
    public ShortsEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                        long userId, long videoId, String thumbnailUrl,
                        String s3Url, ShortsStatus shortsStatus) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.s3Url = s3Url;
        this.shortsStatus = shortsStatus;
    }

    public Shorts toDomain() {
        return Shorts.builder()
                .id(getId())
                .userId(userId)
                .videoId(videoId)
                .thumbnailUrl(thumbnailUrl)
                .s3Url(s3Url)
                .shortsStatus(shortsStatus)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static ShortsEntity from(final Shorts shorts) {
        return ShortsEntity.builder()
                .id(shorts.getId())
                .userId(shorts.getUserId())
                .videoId(shorts.getVideoId())
                .thumbnailUrl(shorts.getThumbnailUrl())
                .s3Url(shorts.getS3Url())
                .shortsStatus(shorts.getShortsStatus())
                .createdAt(shorts.getCreatedAt())
                .updatedAt(shorts.getUpdatedAt())
                .build();
    }
}
