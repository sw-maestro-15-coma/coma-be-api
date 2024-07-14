package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import org.springframework.stereotype.Service;

import static com.swmaestro.cotuber.shorts.ProgressState.COMPLETE;

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

        final Shorts shorts = shortsRepository.findById(task.shortsId())
                .orElseThrow();
        shorts.changeProgressState(COMPLETE);
        shorts.changeLink(link);

        shortsRepository.save(shorts);
    }
}
