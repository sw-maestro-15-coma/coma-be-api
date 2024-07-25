package com.swmaestro.cotuber.batch.dto;

import lombok.Builder;

@Builder
public record AIProcessTask(long userId, long videoId, long shortsId, String youtubeUrl) {
}
