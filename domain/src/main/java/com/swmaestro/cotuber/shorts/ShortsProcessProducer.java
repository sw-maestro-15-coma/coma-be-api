package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageRequest;

public interface ShortsProcessProducer {
    void send(ShortsProcessMessageRequest request);
}
