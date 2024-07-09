package com.swmaestro.cotuber.infra.shorts;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.domain.user.User;
import com.swmaestro.cotuber.infra.video.VideoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "shorts")
public class ShortsEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity video;

    @Column(name = "top_title", length = 100)
    private String topTitle;
}
