package com.swmaestro.cotuber.batch;


import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;

public interface ShortsProcessQueue {
    void push(ShortsProcessTask task);

    boolean isEmpty();

    ShortsProcessTask pop();
}
