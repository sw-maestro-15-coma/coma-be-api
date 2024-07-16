package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessResponse;
import com.swmaestro.cotuber.batch.ShortsProcessQueue;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPoint;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPointRepository;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void getPopularPoint(final AIProcessTask task) {
        log.info("ai processing start");
        final AIProcessResponse response = aiProcessor.process(task.youtubeUrl());
        log.info("ai processing end");

        final ShortsEditPoint editPoint = ShortsEditPoint.initialEditPoint(task.shortsId(), task.videoId());
        final Video video = videoRepository.findById(task.videoId())
                .orElseThrow();
        editPoint.calculateDuration(video.getLength(), response.popularPointSeconds());

        final ShortsEditPoint savedEditPoint = editPointRepository.save(editPoint);

        final Shorts shorts = shortsRepository.findById(task.shortsId())
                        .orElseThrow();
        shorts.changeProgressState(SHORTS_GENERATING);

        shortsProcessQueue.push(
                ShortsProcessTask.builder()
                        .shortsId(task.shortsId())
                        .editPointId(savedEditPoint.getId())
                        .topTitle("테스트 제목")    // 논의 필요 : 언제 쇼츠 제목을 넣어주는지?
                        .s3Url(video.getS3Url())
                        .start(savedEditPoint.getFormattedStart())
                        .end(savedEditPoint.getFormattedEnd()).build()
        );
    }
}
