package com.swmaestro.cotuber.userVideoRelation;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.userVideoRelation.domain.UserVideoRelation;
import com.swmaestro.cotuber.userVideoRelation.domain.UserVideoRelationStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_video_relation")
public class UserVideoRelationEntity extends BaseEntity {
    @Column(name = "user_id")
    private long userId;

    @Column(name = "video_id")
    private long videoId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserVideoRelationStatus userVideoRelationStatus;

    @Builder
    public UserVideoRelationEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                                   long userId, long videoId, UserVideoRelationStatus userVideoRelationStatus) {
        super(id, createdAt, updatedAt);
        this.userId = userId;
        this.videoId = videoId;
        this.userVideoRelationStatus = userVideoRelationStatus;
    }

    public UserVideoRelation toDomain() {
        return UserVideoRelation.builder()
                .id(getId())
                .userId(userId)
                .videoId(videoId)
                .userVideoRelationStatus(userVideoRelationStatus)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static UserVideoRelationEntity from(final UserVideoRelation userVideoRelation) {
        return UserVideoRelationEntity.builder()
                .id(userVideoRelation.getId())
                .userId(userVideoRelation.getUserId())
                .videoId(userVideoRelation.getVideoId())
                .userVideoRelationStatus(userVideoRelation.getUserVideoRelationStatus())
                .createdAt(userVideoRelation.getCreatedAt())
                .updatedAt(userVideoRelation.getUpdatedAt())
                .build();
    }
}
