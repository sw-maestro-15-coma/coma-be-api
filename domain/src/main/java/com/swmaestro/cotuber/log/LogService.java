package com.swmaestro.cotuber.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogService {
    private final LogRepository logRepository;

    public void sendShortsFailLog(long shortsId, String message) {
        logRepository.save(Log.builder()
                .shortsId(shortsId)
                .message(message)
                .build());
    }

    public void sendVideoFailLog(long videoId, String message) {
        logRepository.save(Log.builder()
                .videoId(videoId)
                .message(message)
                .build());

    }

    public void sendDraftFailLog(long draftId, String message) {
        logRepository.save(Log.builder()
                .draftId(draftId)
                .message(message)
                .build());
    }
}
