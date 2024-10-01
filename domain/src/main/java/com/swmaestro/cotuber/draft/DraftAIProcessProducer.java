package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.draft.dto.DraftAIProcessMessageRequest;

public interface DraftAIProcessProducer {
    void send(DraftAIProcessMessageRequest request);
}
