package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessMessageResponse;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelation;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AfterAIProcessService {
    private final UserVideoRelationRepository userVideoRelationRepository;

    public void postProcess(AIProcessMessageResponse response) {
        UserVideoRelation userVideoRelation = userVideoRelationRepository
                .findById(response.userVideoRelationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 relation이 없습니다"));

        userVideoRelation.completeAiProcessing();
        userVideoRelationRepository.save(userVideoRelation);

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
