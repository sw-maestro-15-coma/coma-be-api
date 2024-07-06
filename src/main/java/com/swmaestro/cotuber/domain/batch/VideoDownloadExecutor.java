package com.swmaestro.cotuber.domain.batch;

import com.swmaestro.cotuber.domain.video.VideoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VideoDownloadExecutor {
    private final VideoService videoService;

    public VideoDownloadExecutor(VideoService videoService) {
        this.videoService = videoService;
    }

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        videoService.downloadYoutube();
    }
}
