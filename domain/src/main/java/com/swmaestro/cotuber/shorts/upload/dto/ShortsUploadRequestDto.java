package com.swmaestro.cotuber.shorts.upload.dto;

import com.swmaestro.cotuber.shorts.domain.ShortsUploadType;

public record ShortsUploadRequestDto(
        ShortsUploadType type,
        String email,
        String password,
        String description
) {
}
