package com.swmaestro.cotuber.batch;

import com.swmaestro.cotuber.ai.AIProcessService;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AIProcessExecutor {
    private final AIProcessService service;
    private final AIProcessQueue queue;

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        if (queue.isEmpty()) {
            return;
        }

        final AIProcessTask task = queue.pop();
        service.getPopularPoint(task);
    }
}
