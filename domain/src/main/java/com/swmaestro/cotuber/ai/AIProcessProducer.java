package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;

public interface AIProcessProducer {
    void send(AIProcessMessageRequest request);
}
