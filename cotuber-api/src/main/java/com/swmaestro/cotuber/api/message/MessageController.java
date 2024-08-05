package com.swmaestro.cotuber.api.message;

import com.swmaestro.cotuber.ai.AfterAIProcessService;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageResponse;
import com.swmaestro.cotuber.shorts.AfterShortsProcessService;
import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.AfterVideoDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final AfterVideoDownloadService afterVideoDownloadService;
    private final AfterAIProcessService afterAIProcessService;
    private final AfterShortsProcessService afterShortsProcessService;

    @PostMapping("/video")
    public void receiveVideoDownload(@RequestBody VideoDownloadMessageResponse response) {
        afterVideoDownloadService.postProcess(response);
    }

    @PostMapping("/ai")
    public void receiveAIProcessing(@RequestBody AIProcessMessageResponse response) {
        afterAIProcessService.postProcess(response);
    }

    @PostMapping("/shorts")
    public void receiveShortsProcessing(@RequestBody ShortsProcessMessageResponse response) {
        afterShortsProcessService.postProcessing(response);
    }
}
