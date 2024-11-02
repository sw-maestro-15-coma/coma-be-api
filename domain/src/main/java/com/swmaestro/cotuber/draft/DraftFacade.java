package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.domain.DraftStatus;
import com.swmaestro.cotuber.draft.dto.DraftAIProcessMessageResponse;
import com.swmaestro.cotuber.draft.dto.DraftCreateRequestDto;
import com.swmaestro.cotuber.draft.dto.DraftListResponseDto;
import com.swmaestro.cotuber.draft.dto.DraftResponseDto;
import com.swmaestro.cotuber.edit.EditService;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.VideoService;
import com.swmaestro.cotuber.video.domain.VideoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        List<Draft> draftList = draftService.getDraftList(userId);
        return draftList.stream().map(
                draft -> {
                    Video video = videoService.getVideo(draft.getVideoId());
                    return new DraftListResponseDto(draft, video);
                }).toList();
    }

    public DraftResponseDto getDraft(final long draftId) {
        Draft draft = draftService.getDraft(draftId);
        Video video = videoService.getVideo(draft.getVideoId());
        Edit edit = editService.getEdit(draft.getEditId());
        List<EditSubtitle> editSubtitleList = editService.getEditSubtitleList(draft.getEditId());
        return new DraftResponseDto(draft, video, edit, editSubtitleList);
    }

    public DraftResponseDto createDraft(final long userId, final DraftCreateRequestDto createRequestDto) {
        Optional<Video> video = videoService.findVideoByYoutubeUrl(createRequestDto.youtubeUrl());

        // 신규 요청 생성의 경우 새 비디오 다운로드 요청
        if (video.isEmpty()) {
            Video newVideo = videoService.createVideo(createRequestDto.youtubeUrl());
            videoService.startVideoDownload(newVideo.getId(), newVideo.getYoutubeUrl());
            return createDraftVideoDownloading(userId, newVideo);
        }

        Video presentVideo = video.get();
        switch (presentVideo.getVideoStatus()) {
            case COMPLETE -> {
                return startDraftAIProcess(userId, presentVideo);
            }
            case VIDEO_DOWNLOADING -> {
                return createDraftVideoDownloading(userId, presentVideo);
            }
            case ERROR -> {
                Video updatedVideo = videoService.updateVideoStatus(presentVideo.getId(), VideoStatus.VIDEO_DOWNLOADING);
                videoService.startVideoDownload(updatedVideo.getId(), updatedVideo.getYoutubeUrl());
                return createDraftVideoDownloading(userId, updatedVideo);
            }
            default -> throw new IllegalStateException("Unexpected value: " + presentVideo.getVideoStatus());
        }
    }

    private DraftResponseDto createDraftVideoDownloading(final long userId, final Video video) {
        Edit newEdit = editService.saveEdit(Edit.builder().build());
        Draft newDraft = draftService.saveDraft(
                Draft.builder()
                        .userId(userId)
                        .videoId(video.getId())
                        .editId(newEdit.getId())
                        .draftStatus(DraftStatus.VIDEO_DOWNLOADING)
                        .build()
        );
        return new DraftResponseDto(newDraft, video, newEdit, new ArrayList<>());
    }

    private DraftResponseDto startDraftAIProcess(final long userId, final Video video) {
        Edit newEdit = editService.saveEdit(Edit.builder().build());
        Draft newDraft = draftService.saveDraft(
                Draft.builder()
                        .userId(userId)
                        .videoId(video.getId())
                        .editId(newEdit.getId())
                        .draftStatus(DraftStatus.AI_PROCESSING)
                        .build()
        );
        // 자막 이미 있는거 사용하는 로직 추가
        draftService.startAIProcessByDraftId(newDraft.getId());
        return new DraftResponseDto(newDraft, video, newEdit, new ArrayList<>());
    }
}
