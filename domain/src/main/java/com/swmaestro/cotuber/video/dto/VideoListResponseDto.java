package com.swmaestro.cotuber.video.dto;

import com.swmaestro.cotuber.video.ProgressState;
import lombok.Builder;

@Builder
public record VideoListResponseDto(long id, String title, String link, ProgressState state) {
}
