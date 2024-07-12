package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import org.springframework.stereotype.Service;

@Service
public class ShortsService {
    private final ShortsProcessor shortsProcessor;
    private final ShortsRepository shortsRepository;

    public ShortsService(ShortsProcessor shortsProcessor, ShortsRepository shortsRepository) {
        this.shortsProcessor = shortsProcessor;
        this.shortsRepository = shortsRepository;
    }
    
    public void makeShorts(final ShortsProcessTask task) {
        final String link = shortsProcessor.execute(task);

        final Shorts shorts = Shorts.builder()
                .link(link)
                .build();
        shortsRepository.insert(shorts);
    }
}
