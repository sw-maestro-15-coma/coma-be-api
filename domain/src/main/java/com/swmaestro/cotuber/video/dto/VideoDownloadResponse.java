package com.swmaestro.cotuber.video.dto;

import lombok.Builder;

@Builder
public record VideoDownloadResponse(String s3Url, int length, String originalTitle) {
}
