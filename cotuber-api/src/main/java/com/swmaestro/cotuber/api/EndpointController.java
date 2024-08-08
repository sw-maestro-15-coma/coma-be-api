package com.swmaestro.cotuber.api;

import com.swmaestro.cotuber.auth.NeedLogin;
import com.swmaestro.cotuber.batch.dto.HealthCheckResponseDto;
import com.swmaestro.cotuber.config.AuthUtil;
import com.swmaestro.cotuber.dashboard.DashboardService;
import com.swmaestro.cotuber.dashboard.dto.DashboardListResponseDto;
import com.swmaestro.cotuber.shorts.ShortsService;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import com.swmaestro.cotuber.user.User;
import com.swmaestro.cotuber.user.UserReader;
import com.swmaestro.cotuber.validate.Validator;
import com.swmaestro.cotuber.video.VideoService;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Endpoint", description = "API 서버 엔드포인트")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class EndpointController {
    private final VideoService videoService;
    private final ShortsService shortsService;
    private final DashboardService dashboardService;
    private final UserReader userReader;
    private final Validator validator;

    @NeedLogin
    @Operation(summary = "대시보드의 동영상 목록 조회")
    @GetMapping(value = "/dashboard")
    public List<DashboardListResponseDto> getVideoList() {
        final long userId = AuthUtil.getCurrentUserId();

        return dashboardService.getDashboard(userId);
    }

    @NeedLogin
    @Operation(summary = "유튜브 링크로 동영상 추가(추출)")
    @PostMapping(value = "/video")
    public VideoCreateResponseDto updateVideo(@RequestBody VideoCreateRequestDto createRequestDto) {
        final long userId = AuthUtil.getCurrentUserId();

        validator.checkYoutubeUrl(createRequestDto.youtubeUrl());

        return videoService.requestVideoDownload(userId, createRequestDto);
    }

    @NeedLogin
    @Operation(summary = "생성된 숏폼 데이터의 목록 조회")
    @GetMapping(value = "/shorts")
    public List<ShortsListResponseDto> getShortsList() {
        final long userId = AuthUtil.getCurrentUserId();

        return shortsService.getShorts(userId);
    }

    @Operation(summary = "유저 정보 조회")
    @GetMapping(value = "/user/info")
    public UserInfoResponseDto getUserInfo() {
        try {
            final Long userId = AuthUtil.getCurrentNullishUserId();

            if (userId == null) {
                return UserInfoResponseDto.notLoggedIn();
            }

            User user = userReader.findById(userId);
            return UserInfoResponseDto.loggedIn(user);
        } catch (Exception e) {
            return UserInfoResponseDto.notLoggedIn();
        }
    }

    @Operation(summary = "헬스 체크")
    @GetMapping("/health-check")
    public HealthCheckResponseDto healthCheck() {
        return HealthCheckResponseDto.ok();
    }
}
