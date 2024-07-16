package com.swmaestro.cotuber.shorts;


import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;

public interface ShortsProcessor {
    String execute(ShortsProcessTask task);
}
