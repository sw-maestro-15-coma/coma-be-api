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
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "shorts_id")
    private Long shortsId;
    @Column(name = "progress_context")
    @Enumerated(EnumType.STRING)
    private ProgressContext progressContext;
    @Column(name = "message")
    private String message;

    @Builder
    public LogEntity(Long userId, Long shortsId, ProgressContext progressContext, String message) {
        this.userId = userId;
        this.shortsId = shortsId;
        this.progressContext = progressContext;
        this.message = message;
    }

    public static LogEntity from(Log log) {
        return LogEntity.builder()
                .userId(log.userId())
                .shortsId(log.shortsId())
                .message(log.message())
                .progressContext(log.progressContext())
                .build();
    }

    public Log toDomain() {
        return Log.builder()
                .userId(userId)
                .shortsId(shortsId)
                .message(message)
                .progressContext(progressContext)
                .build();
    }
}
