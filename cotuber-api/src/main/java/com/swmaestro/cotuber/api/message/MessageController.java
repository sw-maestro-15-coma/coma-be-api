package com.swmaestro.cotuber.api.message;

import com.swmaestro.cotuber.draft.DraftFacade;
import com.swmaestro.cotuber.draft.dto.DraftAIProcessMessageResponse;
import com.swmaestro.cotuber.log.LogFacade;
import com.swmaestro.cotuber.log.LogService;
import com.swmaestro.cotuber.log.dto.DraftAIProcessFailResponse;
import com.swmaestro.cotuber.log.dto.ShortsGenerateFailResponse;
import com.swmaestro.cotuber.log.dto.VideoDownloadFailResponse;
import com.swmaestro.cotuber.log.dto.VideoSubtitleGenerateFailResponse;
import com.swmaestro.cotuber.shorts.ShortsFacade;
import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageResponse;
import com.swmaestro.cotuber.video.VideoFacade;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "message endpoint", description = "각 처리 서버 consumer 전용 엔드포인트")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final LogFacade logFacade;
    private final ShortsFacade shortsFacade;
    private final DraftFacade draftFacade;
    private final VideoFacade videoFacade;

    @Operation(summary = "원본 비디오 다운로드 성공")
    @PostMapping("/video")
    public void receiveVideoDownload(@RequestBody VideoDownloadMessageResponse response) {
        videoFacade.afterVideoDownload(response);
    }

    @Operation(summary = "자막 생성")
    @PostMapping("/subtitle")
    public void receiveAISubtitleProcessing(@RequestBody VideoSubtitleGenerateMessageResponse response) {
        videoFacade.afterVideoSubtitleGenerate(response);
    }

    @Operation(summary = "AI 처리")
    @PostMapping("/ai")
    public void receiveAITitleProcessing(@RequestBody DraftAIProcessMessageResponse response) {
        draftFacade.afterAIProcess(response);
    }

    @Operation(summary = "shorts 생성 성공")
    @PostMapping("/shorts")
    public void receiveShortsProcessing(@RequestBody ShortsGenerateMessageResponse response) {
        shortsFacade.afterShortsGenerate(response);
    }

    @Operation(summary = "비디오 다운로드 중 오류 발생")
    @PostMapping("/video/fail")
    public void afterFail(@RequestBody VideoDownloadFailResponse response) {
        logFacade.videoFail(response);
    }

    @Operation(summary = "자막 생성 중 오류 발생")
    @PostMapping("/subtitle/fail")
    public void afterFail(@RequestBody VideoSubtitleGenerateFailResponse response) {
        logFacade.subtitleFail(response);
    }

    @Operation(summary = "AI 처리 중 오류 발생")
    @PostMapping("/ai/fail")
    public void afterFail(@RequestBody DraftAIProcessFailResponse response) {
        logFacade.aiFail(response);
    }

    @Operation(summary = "shorts 생성 중 오류 발생")
    @PostMapping("/shorts/fail")
    public void afterFail(@RequestBody ShortsGenerateFailResponse response) {
        logFacade.shortsFail(response);
    }
}
