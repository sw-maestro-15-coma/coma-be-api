package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import com.swmaestro.cotuber.draft.dto.DraftAIProcessMessageResponse;
import com.swmaestro.cotuber.draft.dto.DraftCreateRequestDto;
import com.swmaestro.cotuber.draft.dto.DraftListResponseDto;
import com.swmaestro.cotuber.draft.dto.DraftResponseDto;
import com.swmaestro.cotuber.edit.EditService;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DraftFacade {
    private final VideoService videoService;
    private final DraftService draftService;
    private final EditService editService;

    public void afterAIProcess(final DraftAIProcessMessageResponse response) {
        Draft draft = draftService.getDraft(response.draftId());
        editService.saveEdit(
                Edit.builder()
                        .id(draft.getEditId())
                        .title(response.title())
                        .start(response.start())
                        .end(response.end())
                        .build()
        );
    }

    public List<DraftListResponseDto> getDraftList(final long userId) {
        List<Draft> Drafts = draftService.getDraftList(userId);
        return Drafts.stream()
                .map(DraftListResponseDto::new)
                .toList();
    }

    public DraftResponseDto getDraft(final long draftId) {
        Draft Draft = draftService.getDraft(draftId);
        return new DraftResponseDto(Draft);
    }

    // need to improve
    public DraftResponseDto createDraft(final long userId, final DraftCreateRequestDto createRequestDto) {
        Optional<Video> video = videoService.findVideoByYoutubeUrl(createRequestDto.youtubeUrl());

        // 비디오 다운되어있지 않은 경우 새 비디오 다운로드 요청
        if (video.isEmpty()) {
            return startVideoDownload(userId, createRequestDto.youtubeUrl());
        }

        Video presentVideo = video.get();
        switch (presentVideo.getVideoStatus()) {
            case COMPLETE -> {
                return startDraftAIProcess(userId, presentVideo.getId());
            }
            case VIDEO_DOWNLOADING -> {
                return createDraftVideoDownloading(userId, presentVideo.getId());
            }
            case ERROR -> {
                return startVideoDownload(userId, createRequestDto.youtubeUrl());
            }
            default -> throw new IllegalStateException("Unexpected value: " + presentVideo.getVideoStatus());
        }
    }

    private DraftResponseDto createDraftVideoDownloading(final long userId, final long videoId) {
        Edit newEdit = editService.saveEdit(Edit.builder().build());
        Draft newDraft = draftService.saveDraft(
                Draft.builder()
                        .userId(userId)
                        .videoId(videoId)
                        .editId(newEdit.getId())
                        .draftStatus(DraftStatus.VIDEO_DOWNLOADING)
                        .build()
        );
        return new DraftResponseDto(newDraft);
    }

    private DraftResponseDto startDraftAIProcess(final long userId, final long videoId) {
        Edit newEdit = editService.saveEdit(Edit.builder().build());
        Draft newDraft = draftService.saveDraft(
                Draft.builder()
                        .userId(userId)
                        .videoId(videoId)
                        .editId(newEdit.getId())
                        .draftStatus(DraftStatus.AI_PROCESSING)
                        .build()
        );
        draftService.startAIProcessByDraftId(newDraft.getId());
        return new DraftResponseDto(newDraft);
    }


    private DraftResponseDto startVideoDownload(final long userId, final String youtubeUrl) {
        Video newVideo = videoService.startVideoDownload(youtubeUrl);
        return createDraftVideoDownloading(userId, newVideo.getId());
    }
}
