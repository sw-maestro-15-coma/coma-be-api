package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.video.domain.EditSubtitle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "video_subtitle")
public class VideoSubtitleEntity extends BaseEntity {
    @Column(name = "video_id")
    private long videoId;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "start")
    private int start;
    @Column(name = "end")
    private int end;

    @Builder
    public VideoSubtitleEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                         long videoId, String subtitle, int start, int end) {
        super(id, createdAt, updatedAt);
        this.videoId = videoId;
        this.subtitle = subtitle;
        this.start = start;
        this.end = end;
    }

    public EditSubtitle toDomain() {
        return EditSubtitle.builder()
                .id(getId())
                .videoId(getVideoId())
                .subtitle(getSubtitle())
                .start(getStart())
                .end(getEnd())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static VideoSubtitleEntity from(EditSubtitle editSubtitle) {
        return VideoSubtitleEntity.builder()
                .id(editSubtitle.getId())
                .videoId(editSubtitle.getVideoId())
                .subtitle(editSubtitle.getSubtitle())
                .start(editSubtitle.getStart())
                .end(editSubtitle.getEnd())
                .createdAt(editSubtitle.getCreatedAt())
                .updatedAt(editSubtitle.getUpdatedAt())
                .build();
    }
}
