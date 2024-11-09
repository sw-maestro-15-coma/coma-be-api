package com.swmaestro.cotuber.log;

import lombok.Builder;

@Builder
public record Log(
        Long videoId,
        Long draftId,
        Long shortsId,
        String message
) {
}
