package com.swmaestro.cotuber.draft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DraftResponseDto(
        long id,
        @JsonProperty("status") DraftStatus status,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
    public DraftResponseDto(Draft draft) {
        this(
                draft.getId(),
                draft.getDraftStatus(),
                draft.getCreatedAt(),
                draft.getUpdatedAt()
        );
    }
}
