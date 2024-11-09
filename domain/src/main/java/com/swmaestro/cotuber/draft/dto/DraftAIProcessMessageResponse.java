package com.swmaestro.cotuber.draft.dto;

public record DraftAIProcessMessageResponse(
        long draftId,
        String title,
        int start,
        int end
) {
}