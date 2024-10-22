package com.swmaestro.cotuber.video.dto;

import lombok.Builder;

@Builder
public record VideoDownloadMessageResponse(
        long videoId,
        String s3Url,
        int videoFullSecond,
        String originalTitle
) {}
