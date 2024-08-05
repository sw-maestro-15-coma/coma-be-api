package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.ai.AIProcessProducer;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;
import com.swmaestro.cotuber.shorts.ProgressState;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AfterVideoDownloadService {
    private final VideoRepository videoRepository;
    private final ShortsRepository shortsRepository;
    private final AIProcessProducer aiProcessProducer;

    public void postProcess(VideoDownloadMessageResponse response) {
        changeVideoState(response);
        changeShortsStateToAIProcessing(response.shortsId());
        publishToAIProducer();
    }

    private void changeVideoState(VideoDownloadMessageResponse response) {
        Video video = videoRepository.findById(response.videoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));
        video.updateVideoInfo(response);
        videoRepository.save(video);
    }

    private void changeShortsStateToAIProcessing(long shortsId) {
        Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));

        shorts.changeStateToAIProcessing();
        shortsRepository.save(shorts);
    }

    private void publishToAIProducer() {
        AIProcessMessageRequest request = AIProcessMessageRequest.builder().build();

        aiProcessProducer.send(request);
    }
}
