package com.swmaestro.cotuber.domain.batch.dto;

import lombok.Builder;

@Builder
public record VideoDownloadTask(long id, String youtubeUrl) {
}
