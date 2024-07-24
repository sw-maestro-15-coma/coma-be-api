package com.swmaestro.cotuber.log;

import lombok.Builder;

@Builder
public record Log(Long userId, Long shortsId, ProgressContext progressContext, String message) {
}
