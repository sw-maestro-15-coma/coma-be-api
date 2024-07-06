package com.swmaestro.cotuber.domain.video.dto;

import lombok.Builder;

@Builder
public record VideoDownloadTask(long id, String youtubeUrl) {
}
