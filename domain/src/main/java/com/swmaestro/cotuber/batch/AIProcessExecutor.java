package com.swmaestro.cotuber.batch;

import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AIProcessExecutor {
    private final AIProcessQueue queue;

    public AIProcessExecutor(AIProcessQueue queue) {
        this.queue = queue;
    }

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        if (queue.isEmpty()) {
            return;
        }

        final AIProcessTask task = queue.pop();
        // do something
    }
}
