package com.swmaestro.cotuber.infra.video;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.domain.video.ProgressState;
import com.swmaestro.cotuber.domain.video.Video;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntity {
    @Column(name = "s3_path", length = 100, nullable = false, unique = true)
    private String s3Path;

    @Column(name = "youtube_url", length = 100, nullable = false, unique = true)
    private String youtubeUrl;

    @Column(name = "state")
    private ProgressState state;

    @Builder
    public VideoEntity(String s3Path, String youtubeUrl, ProgressState state) {
        this.s3Path = s3Path;
        this.youtubeUrl = youtubeUrl;
        this.state = state;
    }

    public Video toDomain() {
        return Video.builder()
                .id(getId())
                .s3Path(s3Path)
                .youtubeUrl(youtubeUrl)
                .state(state)
                .build();
    }
}
