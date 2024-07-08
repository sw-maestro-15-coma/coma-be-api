package com.swmaestro.cotuber.domain.batch;

import com.swmaestro.cotuber.domain.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.exception.EmptyProcessQueueException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class ShortsProcessQueue {
    private static final int CAPACITY = 1000;

    private final BlockingQueue<ShortsProcessTask> queue;

    public ShortsProcessQueue() {
        queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    public void push(final ShortsProcessTask task) {
        queue.add(task);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public ShortsProcessTask pop() {
        if (queue.isEmpty()) {
            throw new EmptyProcessQueueException("영상 처리 큐가 비어있습니다.");
        }

        return queue.poll();
    }
}
