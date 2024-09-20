package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.StringUtil;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageResponse;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsProcessProducer;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageRequest;
import com.swmaestro.cotuber.shorts.edit.ShortsEdit;
import com.swmaestro.cotuber.shorts.edit.ShortsEditRepository;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelation;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationRepository;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AfterAIProcessService {
    private final UserVideoRelationRepository userVideoRelations;
    private final UserVideoRelationRepository userVideoRelationRepository;
    private final ShortsProcessProducer shortsProcessProducer;

    public void postProcess(AIProcessMessageResponse response) {
        UserVideoRelation userVideoRelation = userVideoRelationRepository
                .findById(response.userVideoRelationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 relation이 없습니다"));

        userVideoRelation.completeAiProcessing();
        userVideoRelations.save(userVideoRelation);

        // Need impl
        /*
        ShortsEdit shortsEdit = shortsEditPointRepository.save(
                ShortsEdit.of(response.shortsId(),
                        response.videoId(),
                        video.getVideoTotalSecond(),
                        response.popularPointSecond())
        );
         */
    }
}
