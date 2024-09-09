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
    @Column(name = "shorts_id")
    private Long shortsId;
    @Column(name = "message")
    private String message;

    @Builder
    public LogEntity(Long shortsId, String message) {
        this.shortsId = shortsId;
        this.message = message;
    }

    public static LogEntity from(Log log) {
        return LogEntity.builder()
                .shortsId(log.shortsId())
                .message(log.message())
                .build();
    }

    public Log toDomain() {
        return Log.builder()
                .shortsId(shortsId)
                .message(message)
                .build();
    }
}
