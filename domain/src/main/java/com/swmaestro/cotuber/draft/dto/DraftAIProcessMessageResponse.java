package com.swmaestro.cotuber.draft.dto;

import lombok.Builder;

@Builder
public record DraftAIProcessMessageResponse(
        long draftId,
        String title,
        int start,
        int end
) {}