package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.draft.DraftService;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class VideoFacade {
    final private VideoService videoService;
    final private DraftService draftService;

    public void afterVideoDownload(final VideoDownloadMessageResponse response) {
        videoService.completeVideoDownload(response);
        videoService.startVideoSubtitleGenerate(response.videoId());
        draftService.updateDraftStatusToVideoSubtitleGenerateByVideoId(response.videoId());
    }

    public void afterVideoSubtitleGenerate(final VideoSubtitleGenerateMessageResponse response) {
        videoService.completeVideoSubtitleGenerate(response);
        draftService.startAIProcessByVideoId(response.videoId());
    }
}
