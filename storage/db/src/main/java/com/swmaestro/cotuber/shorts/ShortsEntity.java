package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.user.UserEntity;
import com.swmaestro.cotuber.video.ProgressState;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoEntity;
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

    @Column(name = "top_title", length = 100)
    private String topTitle;

    @Builder
    public ShortsEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                        long userId, long videoId, ProgressState progressState,
                        String link, String topTitle) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.videoId = videoId;
        this.progressState = progressState;
        this.link = link;
        this.topTitle = topTitle;
    }

    public Shorts toDomain() {
        return Shorts.builder()
                .id(getId())
                .userId(userId)
                .videoId(videoId)
                .progressState(progressState)
                .link(link)
                .topTitle(topTitle)
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
                .createdAt(shorts.getCreatedAt())
                .updatedAt(shorts.getUpdatedAt())
                .build();
    }
}
