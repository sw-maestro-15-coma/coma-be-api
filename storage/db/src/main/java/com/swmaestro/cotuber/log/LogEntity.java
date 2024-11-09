package com.swmaestro.cotuber.log;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "log")
public class LogEntity extends BaseEntity {
    @Column(name = "video_id")
    private Long videoId;
    @Column(name = "draft_id")
    private Long draftId;
    @Column(name = "shorts_id")
    private Long shortsId;
    @Column(name = "message")
    private String message;

    @Builder
    public LogEntity(Long videoId, Long draftId, Long shortsId, String message) {
        this.videoId = videoId;
        this.draftId = draftId;
        this.shortsId = shortsId;
        this.message = message;
    }

    public static LogEntity from(Log log) {
        return LogEntity.builder()
                .videoId(log.videoId())
                .draftId(log.draftId())
                .shortsId(log.shortsId())
                .message(log.message())
                .build();
    }

    public Log toDomain() {
        return Log.builder()
                .videoId(videoId)
                .draftId(draftId)
                .shortsId(shortsId)
                .message(message)
                .build();
    }
}
