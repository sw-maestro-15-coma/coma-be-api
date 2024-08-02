package com.swmaestro.cotuber.shorts.edit;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "shorts_edit_point")
public class ShortsEditPointEntity extends BaseEntity {
    @Column(name = "shorts_id")
    private long shortsId;
    @Column(name = "video_id")
    private long videoId;
    @Column(name = "start")
    private int start;
    @Column(name = "end")
    private int end;

    @Builder
    public ShortsEditPointEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                                 long shortsId, long videoId, int start, int end) {
        super(id, createdAt, updatedAt);
        this.shortsId = shortsId;
        this.videoId = videoId;
        this.start = start;
        this.end = end;
    }

    public ShortsEditPoint toDomain() {
        return ShortsEditPoint.builder()
                .id(getId())
                .shortsId(shortsId)
                .videoId(videoId)
                .startSecond(start)
                .endSecond(end)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt()).build();
    }

    public static ShortsEditPointEntity from(ShortsEditPoint editPoint) {
        return ShortsEditPointEntity.builder()
                .id(editPoint.getId())
                .shortsId(editPoint.getShortsId())
                .videoId(editPoint.getVideoId())
                .start(editPoint.getStartSecond())
                .end(editPoint.getEndSecond())
                .createdAt(editPoint.getCreatedAt())
                .updatedAt(editPoint.getUpdatedAt()).build();
    }
}
