package com.swmaestro.cotuber.infra.batch;

import com.swmaestro.cotuber.batch.AIProcessQueue;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import com.swmaestro.cotuber.exception.EmptyAIQueueException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class SimpleAIProcessQueue implements AIProcessQueue {
    private static final int CAPACITY = 1000;

    private final BlockingQueue<AIProcessTask> queue;

    public SimpleAIProcessQueue() {
        queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    @Override
    public void push(final AIProcessTask task) {
        queue.add(task);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public AIProcessTask pop() {
        if (queue.isEmpty()) {
            throw new EmptyAIQueueException("AI 큐가 비어있습니다.");
        }

        return queue.poll();
    }
}
