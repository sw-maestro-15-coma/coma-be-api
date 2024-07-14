package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessResponse;

public interface AIProcessor {
    AIProcessResponse process(String youtubeUrl);
}
