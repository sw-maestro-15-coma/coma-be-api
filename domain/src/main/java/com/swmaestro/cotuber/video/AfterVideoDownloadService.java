package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.ai.AIProcessProducer;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelation;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationRepository;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationService;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AfterVideoDownloadService {
    private final UserVideoRelationRepository userVideoRelationRepository;
    private final VideoRepository videoRepository;
    private final AIProcessProducer aiProcessProducer;

    public void postProcess(VideoDownloadMessageResponse response) {
        Video video = videoRepository.findById(response.videoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));

        video.updateVideoInfo(response);
        videoRepository.save(video);

        List<UserVideoRelation> relationList = userVideoRelationRepository.findAllByVideoId(video.getId());
        for (UserVideoRelation relation : relationList) {
            // videoId 로 relation을 검색해서 downloading 중이였던 모든 relation 을 프로세싱에 넣어줌 + 상태변경해줘
            // publishToAIProducer(relation);
        }

        //        publishToAIProducer(video.getYoutubeUrl());
    }
    
    private void publishToAIProducer(String youtubeUrl) {
        AIProcessMessageRequest request = AIProcessMessageRequest.builder()
                .youtubeUrl(youtubeUrl)
                .build();

        aiProcessProducer.send(request);
    }
}
