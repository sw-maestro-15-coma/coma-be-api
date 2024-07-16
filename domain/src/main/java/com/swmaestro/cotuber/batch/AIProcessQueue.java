package com.swmaestro.cotuber.batch;

import com.swmaestro.cotuber.batch.dto.AIProcessTask;

public interface AIProcessQueue {
    void push(AIProcessTask task);
    boolean isEmpty();
    AIProcessTask pop();
}
