package com.swmaestro.cotuber.api;

import com.swmaestro.cotuber.shorts.ShortsService;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import com.swmaestro.cotuber.video.ProgressState;
import com.swmaestro.cotuber.video.VideoService;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.video.dto.VideoListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "Endpoint", description = "API 서버 엔드포인트")
@RequestMapping("/api/v1")
@RestController
public class EndpointController {
    private final VideoService videoService;
    private final ShortsService shortsService;

    public EndpointController(VideoService videoService, ShortsService shortsService) {
        this.videoService = videoService;
        this.shortsService = shortsService;
    }

    @Operation(summary = "대시보드의 동영상 목록 조회")
    @GetMapping(value = "/video")
    public List<VideoListResponseDto> getMockVideoList() {
        return mockVideoList();
    }

    @Operation(summary = "유튜브 링크로 동영상 추가(추출)")
    @PostMapping("/video")
    public VideoCreateResponseDto mockUpdateVideo(@RequestBody VideoCreateRequestDto requestDto) {
        return VideoCreateResponseDto.builder()
                .id(0L)
                .build();
    }

    @Operation(summary = "생성된 숏폼 데이터의 목록 조회")
    @GetMapping("/shorts")
    public List<ShortsListResponseDto> getMockShortsList() {
        return mockShortsList();
    }

    private List<VideoListResponseDto> mockVideoList() {
        final String mockLink = "https://www.youtube.com/watch?v=qgcoZpZvpfM";
        final String mockTitle = "조미료를 잔뜩넣은 스테이크를 구워 보았습니다";

        return List.of(
                VideoListResponseDto.builder()
                        .id(0)
                        .link(mockLink)
                        .title(mockTitle)
                        .state(ProgressState.COMPLETE)
                        .build(),
                VideoListResponseDto.builder()
                        .id(1)
                        .link(mockLink)
                        .title(mockTitle)
                        .state(ProgressState.COMPLETE)
                        .build(),
                VideoListResponseDto.builder()
                        .id(2)
                        .link(mockLink)
                        .title(mockTitle)
                        .state(ProgressState.COMPLETE)
                        .build(),
                VideoListResponseDto.builder()
                        .id(3)
                        .link(mockLink)
                        .title(mockTitle)
                        .state(ProgressState.COMPLETE)
                        .build()
        );
    }

    private List<ShortsListResponseDto> mockShortsList() {
        final String mockLink = "http://www.test.com/shorts";

        return List.of(
                ShortsListResponseDto.builder()
                        .id(0)
                        .link(mockLink + 0)
                        .build(),
                ShortsListResponseDto.builder()
                        .id(1)
                        .link(mockLink + 1)
                        .build(),
                ShortsListResponseDto.builder()
                        .id(2)
                        .link(mockLink + 2)
                        .build(),
                ShortsListResponseDto.builder()
                        .id(3)
                        .link(mockLink + 3)
                        .build()
        );
    }

    @Operation(summary = "대시보드의 동영상 목록 조회")
    @GetMapping(value = "/video", headers = "X-API-VERSION=2")
    public List<VideoListResponseDto> getVideoList() {
        return List.of();
    }

    @Operation(summary = "유튜브 링크로 동영상 추가(추출)")
    @PostMapping(value = "/video", headers = "X-API-VERSION=2")
    public VideoCreateResponseDto updateVideo(@RequestBody VideoCreateRequestDto createRequestDto) {
        final long userId = extractUserId();

        return videoService.requestVideoDownload(userId, createRequestDto);
    }

    @Operation(summary = "생성된 숏폼 데이터의 목록 조회")
    @GetMapping(value = "/shorts", headers = "X-API-VERSION=2")
    public List<ShortsListResponseDto> getShortsList() {
        return List.of();
    }

    private long extractUserId() {
        return 0L;
    }
}
