package com.swmaestro.cotuber.draft.dto;

import lombok.Builder;

@Builder
public record DraftAIProcessMessageRequest(long draftId, String s3Url) {
}
