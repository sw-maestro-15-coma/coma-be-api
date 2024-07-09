package com.swmaestro.cotuber.domain.batch;

import com.swmaestro.cotuber.domain.batch.dto.VideoDownloadTask;

public interface VideoDownloadQueue {
    void push(VideoDownloadTask task);
    boolean isEmpty();
    VideoDownloadTask pop();
}
