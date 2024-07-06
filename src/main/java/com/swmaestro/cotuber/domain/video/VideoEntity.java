package com.swmaestro.cotuber.domain.video;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntity {
    @Column(name = "s3_path", length = 100, nullable = false, unique = true)
    private String s3Path;

    @Column(name = "youtube_url", length = 100, nullable = false, unique = true)
    private String youtubeUrl;

    @Column(name = "youtube_thumbnail_url", length = 100, nullable = false)
    private String youtubeThumbnailUrl;
}
