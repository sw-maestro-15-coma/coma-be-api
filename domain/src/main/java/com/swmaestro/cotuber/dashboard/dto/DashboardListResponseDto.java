package com.swmaestro.cotuber.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swmaestro.cotuber.dashboard.Dashboard;
import com.swmaestro.cotuber.shorts.ProgressState;
import lombok.Builder;

@Builder
public record DashboardListResponseDto(
        @JsonProperty("id") long shortsId,
        String title,
        @JsonProperty("youtube_url") String youtubeUrl,
        @JsonProperty("progress") ProgressState progressState
) {
    public DashboardListResponseDto(Dashboard dashboard) {
        this(dashboard.shortsId(), dashboard.title(), dashboard.youtubeUrl(), dashboard.progressState());
    }
}
