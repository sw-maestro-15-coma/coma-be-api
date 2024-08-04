package com.swmaestro.cotuber.api.message;

import com.swmaestro.cotuber.ai.AIProcessProducer;
import com.swmaestro.cotuber.ai.AfterAIProcessService;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageResponse;
import com.swmaestro.cotuber.shorts.AfterShortsProcessService;
import com.swmaestro.cotuber.shorts.ShortsProcessProducer;
import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageRequest;
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
    private final AIProcessProducer aiProcessProducer;
    private final AfterAIProcessService afterAIProcessService;
    private final ShortsProcessProducer shortsProcessProducer;
    private final AfterShortsProcessService afterShortsProcessService;

    @PostMapping("/video")
    public void receiveVideoDownload(@RequestBody VideoDownloadMessageResponse response) {
        afterVideoDownloadService.postProcess(response);

        AIProcessMessageRequest aiProcessRequest = AIProcessMessageRequest.builder().build();

        aiProcessProducer.send(aiProcessRequest);
    }

    @PostMapping("/ai")
    public void receiveAIProcessing(@RequestBody AIProcessMessageResponse response) {
        afterAIProcessService.postProcess(response);

        ShortsProcessMessageRequest shortsProcessRequest = ShortsProcessMessageRequest.builder().build();

        shortsProcessProducer.send(shortsProcessRequest);
    }

    @PostMapping("/shorts")
    public void receiveShortsProcessing(@RequestBody ShortsProcessMessageResponse response) {
        afterShortsProcessService.postProcessing(response);
    }
}
