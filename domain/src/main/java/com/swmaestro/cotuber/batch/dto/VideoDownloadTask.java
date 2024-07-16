package com.swmaestro.cotuber.batch.dto;

import lombok.Builder;

@Builder
public record VideoDownloadTask(long id, String youtubeUrl) {
}
