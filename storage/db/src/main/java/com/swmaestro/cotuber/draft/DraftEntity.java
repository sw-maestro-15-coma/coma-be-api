package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "draft")
public class DraftEntity extends BaseEntity {
    @Column(name = "user_id")
    private long userId;

    @Column(name = "video_id")
    private long videoId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DraftStatus draftStatus;

    @Builder
    public DraftEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                       long userId, long videoId, DraftStatus draftStatus) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.videoId = videoId;
        this.draftStatus = draftStatus;
    }

    public Draft toDomain() {
        return Draft.builder()
                .id(getId())
                .userId(userId)
                .videoId(videoId)
                .draftStatus(draftStatus)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static DraftEntity from(final Draft draft) {
        return DraftEntity.builder()
                .id(draft.getId())
                .userId(draft.getUserId())
                .videoId(draft.getVideoId())
                .draftStatus(draft.getDraftStatus())
                .createdAt(draft.getCreatedAt())
                .updatedAt(draft.getUpdatedAt())
                .build();
    }
}
