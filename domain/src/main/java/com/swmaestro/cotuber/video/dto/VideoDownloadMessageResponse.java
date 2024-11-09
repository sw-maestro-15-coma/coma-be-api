package com.swmaestro.cotuber.video.dto;

public record VideoDownloadMessageResponse(
        long videoId,
        String s3Url,
        int videoFullSecond,
        String originalTitle
) {
}
