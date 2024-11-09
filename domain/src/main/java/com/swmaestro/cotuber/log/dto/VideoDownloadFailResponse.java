package com.swmaestro.cotuber.log.dto;

public record VideoDownloadFailResponse(
        long videoId,
        String message
) {
}
