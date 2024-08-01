package com.swmaestro.cotuber.dashboard;

import com.swmaestro.cotuber.shorts.ProgressState;

public record DashboardDto(
        long shortsId,
        String title,
        String youtubeUrl,
        ProgressState progressState
) {
    public Dashboard toDashboard() {
        return Dashboard.builder()
                .shortsId(shortsId)
                .title(title)
                .youtubeUrl(youtubeUrl)
                .progressState(progressState)
                .build();
    }
}
