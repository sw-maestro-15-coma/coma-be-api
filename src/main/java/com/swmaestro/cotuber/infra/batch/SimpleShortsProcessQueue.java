package com.swmaestro.cotuber.infra.batch;

import com.swmaestro.cotuber.domain.batch.ShortsProcessQueue;
import com.swmaestro.cotuber.domain.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.exception.EmptyProcessQueueException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class SimpleShortsProcessQueue implements ShortsProcessQueue {
    private static final int CAPACITY = 1000;

    private final BlockingQueue<ShortsProcessTask> queue;

    public SimpleShortsProcessQueue() {
        queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    @Override
    public void push(final ShortsProcessTask task) {
        queue.add(task);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public ShortsProcessTask pop() {
        if (queue.isEmpty()) {
            throw new EmptyProcessQueueException("영상 처리 큐가 비어있습니다.");
        }

        return queue.poll();
    }
}
