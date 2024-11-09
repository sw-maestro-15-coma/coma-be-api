package com.swmaestro.cotuber.log;


import com.swmaestro.cotuber.draft.DraftService;
import com.swmaestro.cotuber.log.dto.DraftAIProcessFailResponse;
import com.swmaestro.cotuber.log.dto.ShortsGenerateFailResponse;
import com.swmaestro.cotuber.log.dto.VideoDownloadFailResponse;
import com.swmaestro.cotuber.log.dto.VideoSubtitleGenerateFailResponse;
import com.swmaestro.cotuber.shorts.ShortsService;
import com.swmaestro.cotuber.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogFacade {

    private final VideoService videoService;
    private final LogService logService;
    private final DraftService draftService;
    private final ShortsService shortsService;

    public void videoFail(VideoDownloadFailResponse response) {
        videoService.errorVideoDownload(response.videoId());
        draftService.updateDraftStatusToErrorByVideoId(response.videoId());
        logService.sendVideoFailLog(response.videoId(), response.message());
    }

    public void subtitleFail(VideoSubtitleGenerateFailResponse response) {
        videoService.errorSubtitleGenerate(response.videoId());
        draftService.updateDraftStatusToErrorByVideoId(response.videoId());
        logService.sendVideoFailLog(response.videoId(), response.message());
    }

    public void aiFail(DraftAIProcessFailResponse response) {
        draftService.updateDraftStatusToError(response.draftId());
        logService.sendDraftFailLog(response.draftId(), response.message());
    }

    public void shortsFail(ShortsGenerateFailResponse response) {
        shortsService.errorShortsGenerate(response.shortsId());
        logService.sendShortsFailLog(response.shortsId(), response.message());
    }
}