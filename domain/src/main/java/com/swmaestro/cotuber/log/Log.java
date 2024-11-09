package com.swmaestro.cotuber.log;

import lombok.Builder;

@Builder
public record Log(
        long videoId,
        long draftId,
        long shortsId,
        String message
) {
}
