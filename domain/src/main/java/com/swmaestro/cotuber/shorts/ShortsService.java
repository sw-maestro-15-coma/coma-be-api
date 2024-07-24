package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.exception.ShortsMakingFailException;
import com.swmaestro.cotuber.log.Log;
import com.swmaestro.cotuber.log.LogRepository;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.swmaestro.cotuber.log.ProgressContext.SHORTS_GENERATING;
import static com.swmaestro.cotuber.log.ProgressContext.YOUTUBE_DOWNLOADING;
import static com.swmaestro.cotuber.shorts.ProgressState.COMPLETE;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShortsService {
    private final ShortsProcessor shortsProcessor;
    private final ShortsRepository shortsRepository;
    private final LogRepository logRepository;
    
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
            shortsGeneratingFailLog(task.userId(), task.shortsId(), e.getMessage());
            throw new ShortsMakingFailException("shorts 생성에 실패했습니다");
        }
        log.info("shorts processing end");
        shortsGeneratingSuccessLog(task.userId(), task.shortsId());

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

    private void shortsGeneratingSuccessLog(long userId, long shortsId) {
        logRepository.save(Log.builder()
                .message("SUCCESS")
                .progressContext(SHORTS_GENERATING)
                .userId(userId)
                .shortsId(shortsId)
                .build()
        );
    }

    private void shortsGeneratingFailLog(long userId, long shortsId, String message) {
        logRepository.save(Log.builder()
                .message(message)
                .progressContext(SHORTS_GENERATING)
                .shortsId(shortsId)
                .userId(userId)
                .build()
        );
    }
}
