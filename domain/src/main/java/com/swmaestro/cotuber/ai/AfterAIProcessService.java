package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessMessageResponse;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsProcessProducer;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageRequest;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPoint;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPointRepository;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AfterAIProcessService {
    private final ShortsRepository shortsRepository;
    private final VideoRepository videoRepository;
    private final ShortsEditPointRepository shortsEditPointRepository;
    private final ShortsProcessProducer shortsProcessProducer;

    public void postProcess(AIProcessMessageResponse response) {
        changeShortsStateToShortsGenerating(response.shortsId());
        int popularPointSecond = 0;

        Video video = videoRepository.findById(response.videoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));

        ShortsEditPoint shortsEditPoint = shortsEditPointRepository.save(
                ShortsEditPoint.of(response.shortsId(),
                        response.videoId(),
                        video.getVideoTotalSecond(),
                        popularPointSecond)
        );

        publishRequestToShortsProducer(video, response.shortsId(), shortsEditPoint);
    }

    private void changeShortsStateToShortsGenerating(long shortsId) {
        Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));
        shorts.changeStateToShortsGenerating();
        shortsRepository.save(shorts);
    }

    private void publishRequestToShortsProducer(Video video, long shortsId, ShortsEditPoint shortsEditPoint) {
        ShortsProcessMessageRequest request = ShortsProcessMessageRequest.builder()
                .videoId(video.getId())
                .shortsId(shortsId)
                .videoS3Url(video.getS3Url())
                .startSecond(shortsEditPoint.getStartSecond())
                .endSecond(shortsEditPoint.getEndSecond())
                .build();
        shortsProcessProducer.send(request);
    }
}
