package com.swmaestro.cotuber.ai.dto;

import lombok.Builder;

@Builder
public record AIProcessMessageRequest(String youtubeUrl) {
}
