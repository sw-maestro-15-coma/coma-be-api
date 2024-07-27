package com.swmaestro.cotuber.batch.dto;

import lombok.Builder;

@Builder
public record VideoDownloadTask(long userId, long videoId, long shortsId,
                                String youtubeUrl) {
}
