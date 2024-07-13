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
@Table(name = "shorts_edit_point")
public class ShortsEditPoint extends BaseEntity {
    @Column(name = "shorts_id")
    private long shortsId;
    @Column(name = "video_id")
    private long videoId;
    @Column(name = "start")
    private int start;
    @Column(name = "end")
    private int end;

    @Builder
    public ShortsEditPoint(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                           long shortsId, long videoId, int start, int end) {
        super(id, createdAt, updatedAt);
        this.shortsId = shortsId;
        this.videoId = videoId;
        this.start = start;
        this.end = end;
    }
}
