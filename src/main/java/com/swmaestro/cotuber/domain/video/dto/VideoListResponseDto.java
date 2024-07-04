package com.swmaestro.cotuber.domain.video.dto;

import com.swmaestro.cotuber.common.ProgressState;
import lombok.Builder;

@Builder
public record VideoListResponseDto(long id, String title, String link, ProgressState state) {
}
