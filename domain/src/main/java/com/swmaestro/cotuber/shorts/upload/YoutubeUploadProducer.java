package com.swmaestro.cotuber.shorts.upload;

import com.swmaestro.cotuber.shorts.upload.dto.YoutubeUploadMessageRequest;

public interface YoutubeUploadProducer {
    void send(YoutubeUploadMessageRequest request);
}
