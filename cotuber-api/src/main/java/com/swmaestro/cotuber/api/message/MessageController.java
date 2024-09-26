package com.swmaestro.cotuber.api.message;

import com.swmaestro.cotuber.ai.AfterAIProcessService;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageEditResponse;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageSubtitleResponse;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageTitleResponse;
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

    @Operation(summary = "AI 처리 - 제목 생성 성공")
    @PostMapping("/ai/title")
    public void receiveAITitleProcessing(@RequestBody AIProcessMessageTitleResponse response) {
        afterAIProcessService.postTitleProcess(response);
    }

    @Operation(summary = "AI 처리 - 자막 생성 성공")
    @PostMapping("/ai/subtitle")
    public void receiveAISubtitleProcessing(@RequestBody AIProcessMessageSubtitleResponse response) {
        afterAIProcessService.postSubtitleProcess(response);
    }

    @Operation(summary = "AI 처리 - 편집점 생성 성공")
    @PostMapping("/ai/edit")
    public void receiveAIEditProcessing(@RequestBody AIProcessMessageEditResponse response) {
        afterAIProcessService.postEditProcess(response);
    }

    @Operation(summary = "shorts 생성 성공")
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
