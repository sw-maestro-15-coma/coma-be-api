package com.swmaestro.cotuber.infra.batch;

import com.swmaestro.cotuber.batch.VideoDownloadQueue;
import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.exception.EmptyDownloadQueueException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class SimpleVideoDownloadQueue implements VideoDownloadQueue {
    private static final int CAPACITY = 1000;

    private final BlockingQueue<VideoDownloadTask> queue;

    public SimpleVideoDownloadQueue() {
        this.queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    @Override
    public void push(final VideoDownloadTask task) {
        queue.add(task);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public VideoDownloadTask pop() {
        if (queue.isEmpty()) {
            throw new EmptyDownloadQueueException("다운로드 큐가 비어있습니다.");
        }

        return queue.poll();
    }
}
