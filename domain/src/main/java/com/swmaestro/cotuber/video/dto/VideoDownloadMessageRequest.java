package com.swmaestro.cotuber.video.dto;

import lombok.Builder;

@Builder
public record VideoDownloadMessageRequest(
        long videoId,
        String youtubeUrl
) {}
