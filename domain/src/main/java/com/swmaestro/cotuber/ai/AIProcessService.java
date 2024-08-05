package com.swmaestro.cotuber.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class AIProcessService {

    /*
    public void getPopularPoint(final AIProcessTask task) {
        log.info("ai processing start");
        AIProcessResponse response = getAiProcessResponse(task);
        log.info("ai processing end");

        logService.sendSuccessLog(task.userId(), task.shortsId(), AI_PROCESSING);

        final Video video = videoRepository.findById(task.videoId())
                .orElseThrow();

        ShortsEditPoint editPoint = editPointRepository.save(
                ShortsEditPoint.of(
                        task.shortsId(),
                        task.videoId(),
                        video.getVideoTotalSecond(),
                        response.popularPointSeconds()
                )
        );

        final Shorts shorts = shortsRepository.findById(task.shortsId())
                .orElseThrow();
        shorts.changeProgressState(SHORTS_GENERATING);
        shortsRepository.save(shorts);

        shortsProcessQueue.push(
                ShortsProcessTask.builder()
                        .userId(task.userId())
                        .shortsId(task.shortsId())
                        .editPointId(editPoint.getId())
                        .topTitle(shorts.getTopTitle())
                        .s3Url(video.getS3Url())
                        .start(editPoint.getStartSecond())
                        .end(editPoint.getEndSecond())
                        .build()
        );
    }

    private AIProcessResponse getAiProcessResponse(AIProcessTask task) {
        try {
            return aiProcessor.process(task.youtubeUrl());
        } catch (Exception e) {
            log.info("ai 처리 실패 : {}", e.getMessage());
            log.info("shorts id : {}", task.shortsId());

            setShortsStatusToError(task.shortsId());
            logService.sendFailLog(task.userId(), task.shortsId(), AI_PROCESSING, e.getMessage());

            throw new AIProcessFailException("AI 처리 중 오류가 발생했습니다");
        }
    }

    private void setShortsStatusToError(long shortsId) {
        final Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow();
        shorts.changeStateError();
        shortsRepository.save(shorts);
    }

     */
}
