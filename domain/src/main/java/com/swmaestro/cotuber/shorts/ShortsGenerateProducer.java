package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageRequest;

public interface ShortsGenerateProducer {
    void send(ShortsGenerateMessageRequest request);
}
