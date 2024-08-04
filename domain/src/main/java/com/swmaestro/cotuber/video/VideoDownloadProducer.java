package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;

public interface VideoDownloadProducer {
    void send(VideoDownloadMessageRequest request);
}
