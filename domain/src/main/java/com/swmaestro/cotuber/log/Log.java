package com.swmaestro.cotuber.log;

import lombok.Builder;

@Builder
public record Log(
    Long shortsId,
    String message
) {
}
