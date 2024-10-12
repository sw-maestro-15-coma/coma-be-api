package com.swmaestro.cotuber.draft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.edit.dto.EditResponseDto;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.dto.VideoResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DraftResponseDto(
        long id,
        @JsonProperty("status") DraftStatus status,
        @JsonProperty("video") VideoResponseDto video,
        @JsonProperty("edit") EditResponseDto edit,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt
) {
    public DraftResponseDto(Draft draft, Video video, Edit edit, List<EditSubtitle> editSubtitleList) {
        this(
                draft.getId(),
                draft.getDraftStatus(),
                new VideoResponseDto(video),
                new EditResponseDto(edit, editSubtitleList),
                draft.getCreatedAt(),
                draft.getUpdatedAt()
        );
    }
}
