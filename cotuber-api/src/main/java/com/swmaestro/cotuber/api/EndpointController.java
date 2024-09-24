package com.swmaestro.cotuber.api;

import com.swmaestro.cotuber.auth.NeedLogin;
import com.swmaestro.cotuber.batch.dto.HealthCheckResponseDto;
import com.swmaestro.cotuber.config.AuthUtil;
import com.swmaestro.cotuber.shorts.ShortsService;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import com.swmaestro.cotuber.shorts.dto.ShortsResponseDto;
import com.swmaestro.cotuber.user.User;
import com.swmaestro.cotuber.user.UserReader;
import com.swmaestro.cotuber.userVideoRelation.UserVideoRelationService;
import com.swmaestro.cotuber.userVideoRelation.dto.UserVideoRelationListResponseDto;
import com.swmaestro.cotuber.userVideoRelation.dto.UserVideoRelationResponseDto;
import com.swmaestro.cotuber.validate.Validator;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Endpoint", description = "API 서버 엔드포인트")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class EndpointController {
    private final UserVideoRelationService userVideoRelationService;
    private final ShortsService shortsService;
    private final UserReader userReader;
    private final Validator validator;

    @NeedLogin
    @Operation(summary = "등록된 비디오 데이터 목록 조회")
    @GetMapping(value = "/video-list")
    public List<UserVideoRelationListResponseDto> getVideoList() {
        final long userId = AuthUtil.getCurrentUserId();

        return userVideoRelationService.getVideoList(userId);
    }

    @NeedLogin
    @Operation(summary = "등록된 비디오 데이터 단건 조회")
    @GetMapping(value = "/video/{videoId}")
    public UserVideoRelationResponseDto getVideo(@PathParam("videoId") final Long videoId) {
        final long userId = AuthUtil.getCurrentUserId();

        return userVideoRelationService.getVideo(userId, videoId);
    }

    @NeedLogin
    @Operation(summary = "비디오 등록")
    @PostMapping(value = "/video")
    public UserVideoRelationResponseDto createVideo(@RequestBody VideoCreateRequestDto createRequestDto) {
        final long userId = AuthUtil.getCurrentUserId();

        validator.checkYoutubeUrl(createRequestDto.youtubeUrl());

        return userVideoRelationService.createRelation(userId, createRequestDto);
    }

    @NeedLogin
    @Operation(summary = "등록된 비디오 편집 정보 수정")
    @PutMapping(value = "/video/{videoId}/edit")
    public UserVideoRelationResponseDto updateVideoEdit(
            @PathParam("videoId") final Long videoId,
            @RequestBody VideoCreateRequestDto createRequestDto
    ) {
        final long userId = AuthUtil.getCurrentUserId();

        // editService.updateVideoEdit(userId, createRequestDto);

        return userVideoRelationService.getVideo(userId, videoId);
    }

    @NeedLogin
    @Operation(summary = "등록된 비디오로 숏폼 생성")
    @PostMapping(value = "/video/{videoId}/shorts")
    public void createVideoToShorts(@PathParam("videoId") final Long videoId) {
        final long userId = AuthUtil.getCurrentUserId();

        userVideoRelationService.generateShorts(userId, videoId);
    }

    @NeedLogin
    @Operation(summary = "생성된 숏폼 데이터 목록 조회")
    @GetMapping(value = "/shorts-list")
    public List<ShortsListResponseDto> getShortsList() {
        final long userId = AuthUtil.getCurrentUserId();

        return shortsService.getShortsList(userId);
    }

    @NeedLogin
    @Operation(summary = "생성된 숏폼 데이터 단건 조회")
    @GetMapping(value = "/shorts/{shortsId}")
    public ShortsResponseDto getShorts(@PathVariable("shortsId") final long shortsId) {
        return shortsService.getShorts(shortsId);
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
