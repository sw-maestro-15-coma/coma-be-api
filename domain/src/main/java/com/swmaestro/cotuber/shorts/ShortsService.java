package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.exception.ShortsMakingFailException;
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
        String link;

        try {
            log.info("shorts processing start");
            link = shortsProcessor.execute(task);
        } catch (Exception e) {
            log.info("shorts 생성 중 오류 발생 : {}", e.getMessage());
            log.info("shorts id : {}", task.shortsId());

            final Shorts shorts = shortsRepository.findById(task.shortsId())
                    .orElseThrow();
            shorts.changeStateError();
            shortsRepository.save(shorts);
            throw new ShortsMakingFailException("shorts 생성에 실패했습니다");
        }
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
