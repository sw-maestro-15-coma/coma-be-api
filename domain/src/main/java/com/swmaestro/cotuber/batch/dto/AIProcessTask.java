package com.swmaestro.cotuber.batch.dto;

import lombok.Builder;

@Builder
public record AIProcessTask(long shortsId, String youtubeUrl) {
}
