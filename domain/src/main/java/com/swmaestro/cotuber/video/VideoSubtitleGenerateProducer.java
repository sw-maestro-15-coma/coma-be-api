package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageRequest;

public interface VideoSubtitleGenerateProducer {
    void send(VideoSubtitleGenerateMessageRequest request);
}
