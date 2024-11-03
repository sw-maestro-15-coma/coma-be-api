package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import com.swmaestro.cotuber.draft.dto.DraftAIProcessMessageRequest;
import com.swmaestro.cotuber.draft.dto.SubtitleDto;
import com.swmaestro.cotuber.edit.EditService;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DraftService {
    private final DraftRepository draftRepository;
    private final DraftAIProcessProducer draftAIProcessProducer;
    private final EditService editService;

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

    public List<Draft> startAIProcessByVideoId(final long videoId, final List<VideoSubtitle> videoSubtitles) {
        List<Draft> drafts = draftRepository.findAllByVideoId(videoId)
                .stream()
                .filter(Draft::isSubtitleGenerating)
                .toList();

        for (Draft draft : drafts) {
            draft.updateDraftStatus(DraftStatus.AI_PROCESSING);
            draftRepository.save(draft);
            draftAIProcessProducer.send(DraftAIProcessMessageRequest.from(draft.getId(), videoSubtitles));
        }

        return drafts;
    }

    public void startAIProcessByDraftId(final long draftId) {
        Edit edit = editService.findByDraftId(draftId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 edit이 없습니다"));

        List<EditSubtitle> subtitles = editService.getEditSubtitleList(edit.getId());

        draftAIProcessProducer.send(
                DraftAIProcessMessageRequest.builder()
                        .draftId(draftId)
                        .subtitleList(
                                subtitles.stream()
                                        .map(SubtitleDto::from)
                                        .toList()
                        )
                        .build()
        );
    }
}