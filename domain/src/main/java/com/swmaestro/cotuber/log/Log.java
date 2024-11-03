package com.swmaestro.cotuber.log;

import lombok.Builder;

@Builder
public record Log(
        long shortsId,
        String message
) {
}
