package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessResponse;
import com.swmaestro.cotuber.batch.ShortsProcessQueue;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.exception.AIProcessFailException;
import com.swmaestro.cotuber.log.Log;
import com.swmaestro.cotuber.log.LogRepository;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPoint;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPointRepository;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.swmaestro.cotuber.log.ProgressContext.AI_PROCESSING;
import static com.swmaestro.cotuber.log.ProgressContext.YOUTUBE_DOWNLOADING;
import static com.swmaestro.cotuber.shorts.ProgressState.SHORTS_GENERATING;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIProcessService {
    private final AIProcessor aiProcessor;
    private final ShortsEditPointRepository editPointRepository;
    private final VideoRepository videoRepository;
    private final ShortsProcessQueue shortsProcessQueue;
    private final ShortsRepository shortsRepository;
    private final LogRepository logRepository;

    public void getPopularPoint(final AIProcessTask task) {
        AIProcessResponse response;
        try {
            log.info("ai processing start");
            response = aiProcessor.process(task.youtubeUrl());
        } catch (Exception e) {
            log.info("ai 처리 실패 : {}", e.getMessage());
            log.info("shorts id : {}", task.shortsId());

            final Shorts shorts = shortsRepository.findById(task.shortsId())
                    .orElseThrow();
            shorts.changeStateError();
            shortsRepository.save(shorts);

            aiProcessingFailLog(task.userId(), task.shortsId(), e.getMessage());

            throw new AIProcessFailException("AI 처리 중 오류가 발생했습니다");
        }

        log.info("ai processing end");
        aiProcessingSuccessLog(task.userId(), task.shortsId());

        final ShortsEditPoint editPoint = ShortsEditPoint.initialEditPoint(task.shortsId(), task.videoId());
        final Video video = videoRepository.findById(task.videoId())
                .orElseThrow();
        editPoint.calculateDuration(video.getVideoTotalSecond(), response.popularPointSeconds());

        final ShortsEditPoint savedEditPoint = editPointRepository.save(editPoint);

        final Shorts shorts = shortsRepository.findById(task.shortsId())
                .orElseThrow();
        shorts.changeProgressState(SHORTS_GENERATING);
        shortsRepository.save(shorts);

        shortsProcessQueue.push(
                ShortsProcessTask.builder()
                        .userId(task.userId())
                        .shortsId(task.shortsId())
                        .editPointId(savedEditPoint.getId())
                        .topTitle(shorts.getTopTitle())    // 논의 필요 : 언제 쇼츠 제목을 넣어주는지?
                        .s3Url(video.getS3Url())
                        .start(savedEditPoint.getFormattedStart())
                        .end(savedEditPoint.getFormattedEnd()).build()
        );
    }

    private void aiProcessingSuccessLog(long userId, long shortsId) {
        logRepository.save(Log.builder()
                .message("SUCCESS")
                .progressContext(AI_PROCESSING)
                .userId(userId)
                .shortsId(shortsId)
                .build()
        );
    }

    private void aiProcessingFailLog(long userId, long shortsId, String message) {
        logRepository.save(Log.builder()
                .message(message)
                .progressContext(AI_PROCESSING)
                .shortsId(shortsId)
                .userId(userId)
                .build()
        );
    }
}
