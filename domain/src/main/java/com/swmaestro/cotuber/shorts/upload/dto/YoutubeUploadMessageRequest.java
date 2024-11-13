package com.swmaestro.cotuber.shorts.upload.dto;

import lombok.Builder;

@Builder
public record YoutubeUploadMessageRequest(
        String email,
        String password,
        String title,
        String description,
        String shortsS3Url
) {}
