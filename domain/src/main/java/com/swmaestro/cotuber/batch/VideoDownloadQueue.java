package com.swmaestro.cotuber.batch;


import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;

public interface VideoDownloadQueue {
    void push(VideoDownloadTask task);

    boolean isEmpty();

    VideoDownloadTask pop();
}
