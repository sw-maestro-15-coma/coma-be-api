package com.swmaestro.cotuber.ai.dto;

import lombok.Builder;

@Builder
public record AIProcessMessageRequest(long userVideoRelationId, String s3Url) {
}
