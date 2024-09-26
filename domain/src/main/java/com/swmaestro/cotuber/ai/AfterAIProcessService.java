package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessMessageEditResponse;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageSubtitleResponse;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageTitleResponse;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelation;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AfterAIProcessService {
    private final UserVideoRelationRepository userVideoRelationRepository;

    public void postEditProcess(AIProcessMessageEditResponse response) {
        UserVideoRelation userVideoRelation = userVideoRelationRepository
                .findById(response.userVideoRelationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 relation이 없습니다"));
        // need Impl : 실제 response 데이터 저장
        userVideoRelation.completeAiEditProcessing();
        userVideoRelationRepository.save(userVideoRelation);

    }

    public void postTitleProcess(AIProcessMessageTitleResponse response) {
        UserVideoRelation userVideoRelation = userVideoRelationRepository
                .findById(response.userVideoRelationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 relation이 없습니다"));
        // need Impl : 실제 response 데이터 저장
        userVideoRelation.completeTitleProcessing();
        userVideoRelationRepository.save(userVideoRelation);

    }

    public void postSubtitleProcess(AIProcessMessageSubtitleResponse response) {
        UserVideoRelation userVideoRelation = userVideoRelationRepository
                .findById(response.userVideoRelationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 relation이 없습니다"));
        // need Impl : 실제 response 데이터 저장
        userVideoRelation.completeSubtitleProcessing();
        userVideoRelationRepository.save(userVideoRelation);
    }
}
