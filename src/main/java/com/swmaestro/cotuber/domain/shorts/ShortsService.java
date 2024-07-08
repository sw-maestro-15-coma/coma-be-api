package com.swmaestro.cotuber.domain.shorts;

import com.swmaestro.cotuber.domain.batch.dto.ShortsProcessTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShortsService {
    private final ShortsProcessor shortsProcessor;
    private final ShortsRepository shortsRepository;

    public ShortsService(ShortsProcessor shortsProcessor, ShortsRepository shortsRepository) {
        this.shortsProcessor = shortsProcessor;
        this.shortsRepository = shortsRepository;
    }

    @Transactional
    public void makeShorts(final ShortsProcessTask task) {
        final String link = shortsProcessor.execute(task);

        final Shorts shorts = Shorts.builder()
                .link(link)
                .build();
        shortsRepository.insert(shorts);
    }
}
