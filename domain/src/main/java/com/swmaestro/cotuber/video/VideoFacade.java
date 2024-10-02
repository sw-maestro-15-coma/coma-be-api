package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.draft.DraftService;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.edit.EditService;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class VideoFacade {
    final private VideoService videoService;
    final private DraftService draftService;
    final private EditService editService;

    public void afterVideoDownload(final VideoDownloadMessageResponse response) {
        videoService.completeVideoDownload(response);
        videoService.startVideoSubtitleGenerate(response.videoId());
        draftService.updateDraftStatusToVideoSubtitleGenerateByVideoId(response.videoId());
    }

    public void afterVideoSubtitleGenerate(final VideoSubtitleGenerateMessageResponse response) {
        Video video = videoService.getVideo(response.videoId());

        List<VideoSubtitle> videoSubtitleList = response.subtitleList().stream().map(
            subtitle -> VideoSubtitle.builder()
                .videoId(video.getId())
                .subtitle(subtitle.subtitle())
                .start(subtitle.start())
                .end(subtitle.end())
                .build()
        ).toList();

        videoService.completeVideoSubtitleGenerate(videoSubtitleList);
        List<Draft> startedDraftList = draftService.startAIProcessByVideoId(response.videoId());

        startedDraftList.forEach(draft -> {
            List<EditSubtitle> editSubtitleList = videoSubtitleList.stream().map(
                videoSubtitle -> EditSubtitle.builder()
                    .editId(draft.getEditId())
                    .subtitle(videoSubtitle.getSubtitle())
                    .start(videoSubtitle.getStart())
                    .end(videoSubtitle.getEnd())
                    .build()
            ).toList();
            editService.saveEditSubtitle(editSubtitleList);
        });
    }
}
