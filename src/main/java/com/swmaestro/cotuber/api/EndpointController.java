package com.swmaestro.cotuber.api;

import com.swmaestro.cotuber.domain.shorts.dto.ShortsListResponseDto;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.domain.video.dto.VideoListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.swmaestro.cotuber.common.ProgressState.COMPLETE;

@Tag(name = "Endpoint", description = "API 서버 엔드포인트")
@RequestMapping("/api/v1")
@RestController
public class EndpointController {
    @Operation(summary = "대시보드의 동영상 목록 조회")
    @GetMapping(value = "/video")
    public List<VideoListResponseDto> getVideoList(HttpServletRequest request) {
        return mockVideoList();
    }

    @Operation(summary = "유튜브 링크로 동영상 추가(추출)")
    @PostMapping("/video")
    public VideoCreateResponseDto updateVideo(HttpServletRequest request, @RequestBody VideoCreateRequestDto requestDto) {
        return VideoCreateResponseDto.builder()
                .id(0L)
                .build();
    }

    @Operation(summary = "생성된 숏폼 데이터의 목록 조회")
    @GetMapping("/shorts")
    public List<ShortsListResponseDto> getShortsList(HttpServletRequest request) {
        return mockShortsList();
    }

    private String getUser(final HttpServletRequest request) {
        return "testuser";
    }

    private List<VideoListResponseDto> mockVideoList() {
        final List<VideoListResponseDto> results = new ArrayList<>();

        for (long id = 0; id <= 3; id++) {
            results.add(
                VideoListResponseDto.builder()
                    .id(id)
                    .link("https://www.youtube.com/watch?v=qgcoZpZvpfM")
                    .title("조미료를 잔뜩넣은 스테이크를 구워 보았습니다")
                    .state(COMPLETE)
                    .build()
            );
        }
        return results;
    }

    private List<ShortsListResponseDto> mockShortsList() {
        final List<ShortsListResponseDto> results = new ArrayList<>();

        for (long id = 0; id <= 3; id++) {
            results.add(
                ShortsListResponseDto.builder()
                        .id(id)
                        .link("http://www.test.com/shorts" + id)
                        .build()
            );
        }
        return results;
    }
}
