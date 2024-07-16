package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.swmaestro.cotuber.shorts.ProgressState.COMPLETE;

@Slf4j
@Service
public class ShortsService {
    private final ShortsProcessor shortsProcessor;
    private final ShortsRepository shortsRepository;

    public ShortsService(ShortsProcessor shortsProcessor, ShortsRepository shortsRepository) {
        this.shortsProcessor = shortsProcessor;
        this.shortsRepository = shortsRepository;
    }
    
    public void makeShorts(final ShortsProcessTask task) {
        log.info("shorts processing start");
        final String link = shortsProcessor.execute(task);
        log.info("shorts processing end");

        final Shorts shorts = shortsRepository.findById(task.shortsId())
                .orElseThrow();
        shorts.changeProgressState(COMPLETE);
        shorts.changeLink(link);

        shortsRepository.save(shorts);
    }

    public List<ShortsListResponseDto> getShorts(final long userId) {
        final List<Shorts> shorts = shortsRepository.findAllByUserId(userId);
        final List<ShortsListResponseDto> results = new ArrayList<>();
        shorts.forEach(e -> results.add(
                ShortsListResponseDto.builder()
                        .id(e.getId())
                        .topTitle(e.getTopTitle())
                        .s3Url(e.getLink())
                        .thumbnailUrl(e.getThumbnailUrl()).build()
        ));
        return results;
    }
}
