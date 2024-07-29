package com.swmaestro.cotuber.dashboard;

import com.swmaestro.cotuber.shorts.ProgressState;
import lombok.Builder;

@Builder
public record Dashboard(
        long shortsId,
        String title,
        String youtubeUrl,
        ProgressState progressState
) {
}
