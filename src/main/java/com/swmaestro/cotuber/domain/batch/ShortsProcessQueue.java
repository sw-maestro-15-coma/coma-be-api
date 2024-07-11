package com.swmaestro.cotuber.domain.batch;

import com.swmaestro.cotuber.domain.batch.dto.ShortsProcessTask;

public interface ShortsProcessQueue {
    void push(ShortsProcessTask task);
    boolean isEmpty();
    ShortsProcessTask pop();
}
