package com.swmaestro.cotuber.batch;


import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.video.VideoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VideoDownloadExecutor {
    private final VideoService videoService;
    private final VideoDownloadQueue queue;

    public VideoDownloadExecutor(VideoService videoService, VideoDownloadQueue queue) {
        this.videoService = videoService;
        this.queue = queue;
    }

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        if (queue.isEmpty()) {
            return;
        }

        final VideoDownloadTask task = queue.pop();

        videoService.downloadYoutube(task);
    }
}
