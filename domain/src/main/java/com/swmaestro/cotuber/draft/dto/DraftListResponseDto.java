package com.swmaestro.cotuber.draft.dto;

import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.dto.VideoResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DraftListResponseDto(
        long id,
        DraftStatus status,
        VideoResponseDto video,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public DraftListResponseDto(Draft draft, Video video) {
        this(
                draft.getId(),
                draft.getDraftStatus(),
                new VideoResponseDto(video),
                draft.getCreatedAt(),
                draft.getUpdatedAt()
        );
    }
}
