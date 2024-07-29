package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.exception.ShortsMakingFailException;
import com.swmaestro.cotuber.log.LogService;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.swmaestro.cotuber.log.ProgressContext.*;
import static com.swmaestro.cotuber.shorts.ProgressState.COMPLETE;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShortsService {
    private final ShortsProcessor shortsProcessor;
    private final ShortsRepository shortsRepository;
    private final LogService logService;
    private final ShortsThumbnailMaker shortsThumbnailMaker;

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
            logService.sendFailLog(task.userId(), task.shortsId(), SHORTS_GENERATING, e.getMessage());
            throw new ShortsMakingFailException("shorts 생성에 실패했습니다");
        }
        log.info("shorts processing end");
        logService.sendSuccessLog(task.userId(), task.shortsId(), SHORTS_GENERATING);

        final Shorts shorts = shortsRepository.findById(task.shortsId())
                .orElseThrow();
        shorts.changeProgressState(COMPLETE);
        shorts.changeLink(link);

        shortsRepository.save(shorts);

        String thumbnailUrl = getShortsThumbnailUrl(task.userId(), task.shortsId());
        shorts.changeThumbnailUrl(thumbnailUrl);

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

    private String getShortsThumbnailUrl(final long userId, final long shortsId) {
        try {
            String thumbnailUrl = shortsThumbnailMaker.makeThumbnail(shortsId);
            logService.sendSuccessLog(userId, shortsId, SHORTS_THUMBNAIL_GENERATING);
            return thumbnailUrl;
        } catch (Exception e) {
            log.info("shorts thumbnail 생성 중 오류 발생 : {}", e.getMessage());
            log.info("shorts id : {}", shortsId);

            logService.sendFailLog(userId, shortsId, SHORTS_THUMBNAIL_GENERATING, e.getMessage());
            throw new ShortsMakingFailException("shorts thumbnail 생성에 실패했습니다");
        }
    }
}
