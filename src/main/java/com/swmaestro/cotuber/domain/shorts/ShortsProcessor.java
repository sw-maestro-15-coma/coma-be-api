package com.swmaestro.cotuber.domain.shorts;

import com.swmaestro.cotuber.domain.batch.dto.ShortsProcessTask;

public interface ShortsProcessor {
    String execute(ShortsProcessTask task);
}
