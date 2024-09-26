package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.ai.AIProcessProducer;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelation;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationRepository;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationService;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationStatus;
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

        video.completeVideoDownloading(response);
        videoRepository.save(video);

        List<UserVideoRelation> relationList = userVideoRelationRepository.findAllByVideoId(video.getId());
        for (UserVideoRelation relation : relationList) {
            if (relation.getUserVideoRelationStatus() == UserVideoRelationStatus.VIDEO_DOWNLOADING) {
                relation.completeVideoDownloading();
                userVideoRelationRepository.save(relation);
                publishToAIProducer(relation.getId(), video.getS3Url());
            }
        }
    }

    public void publishToAIProducer(final long userVideoRelationId, final String s3Url) {
        AIProcessMessageRequest request = AIProcessMessageRequest.builder()
                .userVideoRelationId(userVideoRelationId)
                .s3Url(s3Url)
                .build();

        aiProcessProducer.send(request);
    }
}
