package com.swmaestro.cotuber.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogService {
    private final LogRepository logRepository;

    public void sendSuccessLog(Long userId,
                               Long shortsId,
                               ProgressContext context) {
        logRepository.save(Log.builder()
                .userId(userId)
                .shortsId(shortsId)
                .progressContext(context)
                .message("SUCCESS")
                .build());
    }

    public void sendFailLog(Long userId,
                            Long shortsId,
                            ProgressContext context,
                            String message) {
        logRepository.save(Log.builder()
                .userId(userId)
                .shortsId(shortsId)
                .progressContext(context)
                .message(message)
                .build());
    }
}
