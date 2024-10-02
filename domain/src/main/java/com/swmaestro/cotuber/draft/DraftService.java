package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.draft.dto.DraftAIProcessMessageRequest;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DraftService {
    private final DraftRepository draftRepository;
    private final DraftAIProcessProducer draftAIProcessProducer;

    public List<Draft> getDraftList(final long userId) {
        return draftRepository.findAllByUserId(userId);
    }

    public Draft getDraft(final long draftId) {
        return draftRepository.findById(draftId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 draft가 없습니다"));
    }

    public Draft saveDraft(final Draft draft) {
        return draftRepository.save(draft);
    }

    public void updateDraftStatusToVideoSubtitleGenerateByVideoId(final long videoId) {
        List<Draft> draftList = draftRepository.findAllByVideoId(videoId);

        for (Draft draft : draftList) {
            if (draft.getDraftStatus() == DraftStatus.VIDEO_DOWNLOADING) {
                draft.updateDraftStatus(DraftStatus.VIDEO_SUBTITLE_GENERATING);
                draftRepository.save(draft);
            }
        }
    }

    public List<Draft> startAIProcessByVideoId(final long videoId) {
        List<Draft> startedDraftList = new ArrayList<>();
        List<Draft> draftList = draftRepository.findAllByVideoId(videoId);

        for (Draft draft : draftList) {
            if (draft.getDraftStatus() == DraftStatus.VIDEO_SUBTITLE_GENERATING) {
                draft.updateDraftStatus(DraftStatus.AI_PROCESSING);
                draftRepository.save(draft);
                startedDraftList.add(draft);
                draftAIProcessProducer.send(
                        DraftAIProcessMessageRequest.builder()
                                .draftId(draft.getId())
                                // need impl to subtitle
                                .build()
                );
            }
        }
        return startedDraftList;
    }

    public void startAIProcessByDraftId(final long draftId) {
        draftAIProcessProducer.send(
                DraftAIProcessMessageRequest.builder()
                        .draftId(draftId)
                        // need impl to subtitle
                        .build()
        );
    }
}