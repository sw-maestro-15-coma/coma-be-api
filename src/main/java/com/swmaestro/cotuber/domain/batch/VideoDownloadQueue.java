package com.swmaestro.cotuber.domain.batch;

import com.swmaestro.cotuber.domain.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.exception.EmptyDownloadQueueException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class VideoDownloadQueue {
    private static final int CAPACITY = 1000;

    private final BlockingQueue<VideoDownloadTask> queue;

    public VideoDownloadQueue() {
        this.queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    public void push(final VideoDownloadTask task) {
        queue.add(task);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public VideoDownloadTask pop() {
        if (queue.isEmpty()) {
            throw new EmptyDownloadQueueException("다운로드 큐가 비어있습니다.");
        }

        return queue.poll();
    }
}
