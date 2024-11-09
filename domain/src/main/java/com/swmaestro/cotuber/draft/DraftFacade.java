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
import com.swmaestro.cotuber.video.VideoService;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
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
        Edit edit = editService.saveEdit(
                Edit.builder()
                        .draftId(response.draftId())
                        .title(response.title())
                        .start(response.start())
                        .end(response.end())
                        .build()
        );

        Draft draft = draftService.getDraft(response.draftId());
        List<VideoSubtitle> videoSubtitles = videoService.getVideoSubtitlesByVideoId(draft.getVideoId());

        saveEditSubtitles(edit, videoSubtitles);
    }


    private void saveEditSubtitles(Edit edit, List<VideoSubtitle> videoSubtitles) {
        List<EditSubtitle> editSubtitles = videoSubtitles.stream()
                .map(videoSubtitle -> EditSubtitle.from(edit.getId(), videoSubtitle))
                .toList();

        editService.saveEditSubtitle(editSubtitles);
    }

    public List<DraftListResponseDto> getDraftList(final long userId) {
        return draftService.getDraftList(userId)
                .stream()
                .map(draft -> {
                    Video video = videoService.getVideo(draft.getVideoId());
                    return new DraftListResponseDto(draft, video);
                })
                .toList();
    }

    public DraftResponseDto getDraft(final long draftId) {
        Draft draft = draftService.getDraft(draftId);
        Video video = videoService.getVideo(draft.getVideoId());
        Optional<Edit> foundEdit = editService.findByDraftId(draftId);

        if (foundEdit.isEmpty()) {
            return new DraftResponseDto(draft, video);
        }

        Edit edit = foundEdit.get();
        List<EditSubtitle> editSubtitleList = editService.getEditSubtitleList(edit.getId());
        return new DraftResponseDto(draft, video, edit, editSubtitleList);
    }

    public DraftResponseDto createDraft(final long userId, final DraftCreateRequestDto request) {
        Optional<Video> foundVideo = videoService.findVideoByYoutubeUrl(request.youtubeUrl());

        // 신규 요청 생성의 경우 새 비디오 다운로드 요청
        if (foundVideo.isEmpty()) {
            Video newVideo = videoService.createVideo(request.youtubeUrl());
            videoService.startVideoDownload(newVideo);

            Draft newDraft = createDraft(userId, newVideo, DraftStatus.VIDEO_DOWNLOADING);
            return new DraftResponseDto(newDraft, newVideo);
        }

        Video video = foundVideo.get();

        return switch (video.getVideoStatus()) {
            case COMPLETE -> {
                Draft draft = createDraft(userId, video, DraftStatus.AI_PROCESSING);
                List<VideoSubtitle> videoSubtitles = videoService.getVideoSubtitlesByVideoId(video.getId());
                draftService.startAIProcess(draft.getId(), videoSubtitles);
                yield new DraftResponseDto(draft, video);
            }
            case VIDEO_DOWNLOADING -> {
                Draft draft = createDraft(userId, video, DraftStatus.VIDEO_DOWNLOADING);
                yield new DraftResponseDto(draft, video);
            }
            case SUBTITLE_GENERATING -> {
                Draft draft = createDraft(userId, video, DraftStatus.VIDEO_SUBTITLE_GENERATING);
                yield new DraftResponseDto(draft, video);
            }
            case DOWNLOAD_ERROR -> {
                Video downloadStartVideo = videoService.startVideoDownload(video);

                Draft newDraft = createDraft(userId, downloadStartVideo, DraftStatus.VIDEO_DOWNLOADING);
                yield new DraftResponseDto(newDraft, downloadStartVideo);
            }
            case SUBTITLE_GENERATE_ERROR -> {
                videoService.startVideoSubtitleGenerate(video.getId(), video.getS3Url());

                Draft draft = createDraft(userId, video, DraftStatus.VIDEO_SUBTITLE_GENERATING);
                yield new DraftResponseDto(draft, video);
            }
        };
    }

    private Draft createDraft(final long userId, final Video video, DraftStatus draftStatus) {
        return draftService.saveDraft(
                Draft.builder()
                        .userId(userId)
                        .videoId(video.getId())
                        .draftStatus(draftStatus)
                        .build()
        );
    }
}
