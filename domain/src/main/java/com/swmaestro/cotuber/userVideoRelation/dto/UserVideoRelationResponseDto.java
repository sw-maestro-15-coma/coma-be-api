package com.swmaestro.cotuber.userVideoRelation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.userVideoRelation.domain.UserVideoRelation;
import com.swmaestro.cotuber.userVideoRelation.domain.UserVideoRelationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserVideoRelationResponseDto(
        long id,
        @JsonProperty("status") UserVideoRelationStatus status,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
    public UserVideoRelationResponseDto(UserVideoRelation userVideoRelation) {
        this(
                userVideoRelation.getId(),
                userVideoRelation.getUserVideoRelationStatus(),
                userVideoRelation.getCreatedAt(),
                userVideoRelation.getUpdatedAt()
        );
    }
}
