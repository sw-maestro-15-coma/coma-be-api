package com.swmaestro.cotuber.api.message;

import com.swmaestro.cotuber.ai.AfterAIProcessService;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageResponse;
import com.swmaestro.cotuber.log.LogService;
import com.swmaestro.cotuber.log.dto.FailLogMessage;
import com.swmaestro.cotuber.shorts.AfterShortsProcessService;
import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.AfterVideoDownloadService;
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
    private final AfterVideoDownloadService afterVideoDownloadService;
    private final AfterAIProcessService afterAIProcessService;
    private final AfterShortsProcessService afterShortsProcessService;
    private final LogService logService;

    @Operation(summary = "원본 비디오 다운로드 성공")
    @PostMapping("/video")
    public void receiveVideoDownload(@RequestBody VideoDownloadMessageResponse response) {
        afterVideoDownloadService.postProcess(response);
    }

    @Operation(summary = "AI 처리 성공")
    @PostMapping("/ai")
    public void receiveAIProcessing(@RequestBody AIProcessMessageResponse response) {
        afterAIProcessService.postProcess(response);
    }

    @Operation(summary = "shorts 처리 성공")
    @PostMapping("/shorts")
    public void receiveShortsProcessing(@RequestBody ShortsProcessMessageResponse response) {
        afterShortsProcessService.postProcessing(response);
    }

    @Operation(summary = "처리 중 오류 발생")
    @PostMapping("/fail")
    public void afterFail(@RequestBody FailLogMessage logMessage) {
        logService.sendFailLog(logMessage.shortsId(), logMessage.message());
    }
}
