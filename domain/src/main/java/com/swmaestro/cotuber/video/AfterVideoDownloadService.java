package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.ai.AIProcessProducer;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;
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
    private final TopTitleGenerator topTitleGenerator;

    public void postProcess(VideoDownloadMessageResponse response) {
        Shorts shorts = shortsRepository.findById(response.shortsId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));
        Video video = videoRepository.findById(response.videoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));

        String topTitle = generateTopTitle(response.originalTitle());
        shorts.assignTopTitle(topTitle);
        shorts.changeStateToAIProcessing();
        shortsRepository.save(shorts);

        video.updateVideoInfo(response);
        videoRepository.save(video);

        publishToAIProducer(video.getYoutubeUrl());
    }

    private String generateTopTitle(String originalTitle) {
        try {
            return topTitleGenerator.makeTopTitle(originalTitle);
        } catch (Exception e) {
            throw new IllegalStateException("쇼츠 top title 생성에 실패했습니다");
        }
    }

    private void publishToAIProducer(String youtubeUrl) {
        AIProcessMessageRequest request = AIProcessMessageRequest.builder()
                .youtubeUrl(youtubeUrl)
                .build();

        aiProcessProducer.send(request);
    }
}
