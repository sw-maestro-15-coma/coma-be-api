package com.swmaestro.cotuber.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogService {
    private final LogRepository logRepository;

    public void sendFailLog(
            Long shortsId,
            String message
    ) {
        logRepository.save(Log.builder()
                .shortsId(shortsId)
                .message(message)
                .build());
    }
}
