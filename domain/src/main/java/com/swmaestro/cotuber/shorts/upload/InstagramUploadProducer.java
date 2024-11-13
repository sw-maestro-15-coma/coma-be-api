package com.swmaestro.cotuber.shorts.upload;


import com.swmaestro.cotuber.shorts.upload.dto.InstagramUploadMessageRequest;

public interface InstagramUploadProducer {
    void send(InstagramUploadMessageRequest request);
}
