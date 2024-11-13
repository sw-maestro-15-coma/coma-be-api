package com.swmaestro.cotuber.shorts.upload.dto;

import lombok.Builder;

@Builder
public record InstagramUploadMessageRequest(
        String email,
        String password,
        String shortsS3Url,
        String thumbnailUrl,
        String caption
) {}
