package com.swmaestro.cotuber.infra.ai;

import com.swmaestro.cotuber.ai.AIProcessor;
import com.swmaestro.cotuber.ai.dto.AIProcessResponse;
import org.springframework.stereotype.Component;

@Component
public class MockAIProcessor implements AIProcessor {
    @Override
    public AIProcessResponse process(String youtubeUrl) {
        return AIProcessResponse.builder()
                .hotSeconds(10)
                .build();
    }
}
