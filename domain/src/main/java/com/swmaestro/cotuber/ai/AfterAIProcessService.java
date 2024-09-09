package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.StringUtil;
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
        Shorts shorts = shortsRepository.findById(response.shortsId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));
        shorts.changeStateToShortsGenerating();
        shortsRepository.save(shorts);

        Video video = videoRepository.findById(response.videoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));

        ShortsEditPoint shortsEditPoint = shortsEditPointRepository.save(
                ShortsEditPoint.of(response.shortsId(),
                        response.videoId(),
                        video.getVideoTotalSecond(),
                        response.popularPointSecond())
        );

        ShortsProcessMessageRequest request = ShortsProcessMessageRequest.builder()
                .videoId(video.getId())
                .shortsId(response.shortsId())
                .videoS3Url(video.getS3Url())
                .startTime(StringUtil.secondToFormat(shortsEditPoint.getStartSecond()))
                .endTime(StringUtil.secondToFormat(shortsEditPoint.getEndSecond()))
                .topTitle(shorts.getTopTitle())
                .build();
        shortsProcessProducer.send(request);
    }
}
