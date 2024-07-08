package com.swmaestro.cotuber.domain.batch;

import com.swmaestro.cotuber.domain.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.domain.shorts.ShortsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ShortsProcessExecutor {
    private final ShortsService shortsService;
    private final ShortsProcessQueue queue;

    public ShortsProcessExecutor(ShortsService shortsService, ShortsProcessQueue queue) {
        this.shortsService = shortsService;
        this.queue = queue;
    }

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        if (queue.isEmpty()) {
            return;
        }

        final ShortsProcessTask task = queue.pop();

        shortsService.makeShorts(task);
    }
}
