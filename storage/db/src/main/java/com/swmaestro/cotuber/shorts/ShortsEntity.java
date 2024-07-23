package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.common.BaseEntity;
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

    @Column(name = "progress_state")
    @Enumerated(EnumType.STRING)
    private ProgressState progressState;

    @Column(name = "link")
    private String link;

    @Column(name = "top_title")
    private String topTitle;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Builder
    public ShortsEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                        long userId, long videoId, ProgressState progressState,
                        String link, String topTitle, String thumbnailUrl) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.videoId = videoId;
        this.progressState = progressState;
        this.link = link;
        this.topTitle = topTitle;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Shorts toDomain() {
        return Shorts.builder()
                .id(getId())
                .userId(userId)
                .videoId(videoId)
                .progressState(progressState)
                .link(link)
                .topTitle(topTitle)
                .thumbnailUrl(thumbnailUrl)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static ShortsEntity from(final Shorts shorts) {
        return ShortsEntity.builder()
                .id(shorts.getId())
                .userId(shorts.getUserId())
                .videoId(shorts.getVideoId())
                .progressState(shorts.getProgressState())
                .link(shorts.getLink())
                .topTitle(shorts.getTopTitle())
                .thumbnailUrl(shorts.getThumbnailUrl())
                .createdAt(shorts.getCreatedAt())
                .updatedAt(shorts.getUpdatedAt())
                .build();
    }
}
