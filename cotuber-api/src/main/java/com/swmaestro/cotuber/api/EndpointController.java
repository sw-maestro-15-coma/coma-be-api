package com.swmaestro.cotuber.api;

import com.swmaestro.cotuber.auth.NeedLogin;
import com.swmaestro.cotuber.edit.EditFacade;
import com.swmaestro.cotuber.edit.dto.EditRequestDto;
import com.swmaestro.cotuber.edit.dto.EditSubtitleUpdateRequestDto;
import com.swmaestro.cotuber.health.dto.HealthCheckResponseDto;
import com.swmaestro.cotuber.config.AuthUtil;
import com.swmaestro.cotuber.shorts.ShortsFacade;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import com.swmaestro.cotuber.shorts.dto.ShortsResponseDto;
import com.swmaestro.cotuber.user.User;
import com.swmaestro.cotuber.user.UserReader;
import com.swmaestro.cotuber.draft.DraftFacade;
import com.swmaestro.cotuber.draft.dto.DraftListResponseDto;
import com.swmaestro.cotuber.draft.dto.DraftResponseDto;
import com.swmaestro.cotuber.validate.Validator;
import com.swmaestro.cotuber.draft.dto.DraftCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Endpoint", description = "API 서버 엔드포인트")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class EndpointController {
    private final UserReader userReader;
    private final Validator validator;
    private final DraftFacade draftFacade;
    private final ShortsFacade shortsFacade;
    private final EditFacade editFacade;

    @NeedLogin
    @Operation(summary = "등록된 Draft 데이터 목록 조회")
    @GetMapping(value = "/draft-list")
    public List<DraftListResponseDto> getDraftList() {
        final long userId = AuthUtil.getCurrentUserId();

        return draftFacade.getDraftList(userId);
    }

    @NeedLogin
    @Operation(summary = "등록된 Draft 데이터 단건 조회")
    @GetMapping(value = "/draft/{draftId}")
    public DraftResponseDto getDraft(@PathVariable("draftId") final Long draftId) {
        return draftFacade.getDraft(draftId);
    }

    @NeedLogin
    @Operation(summary = "Draft 등록")
    @PostMapping(value = "/draft")
    public DraftResponseDto createRelation(@RequestBody DraftCreateRequestDto draftCreateRequestDto) {
        final long userId = AuthUtil.getCurrentUserId();

        validator.checkYoutubeUrl(draftCreateRequestDto.youtubeUrl());

        return draftFacade.createDraft(userId, draftCreateRequestDto);
    }

    @NeedLogin
    @Operation(summary = "등록된 Draft 편집 정보 수정")
    @PutMapping(value = "/draft/{draftId}/edit")
    public DraftResponseDto updateEdit(
            @PathVariable("draftId") final Long draftId,
            @RequestBody EditRequestDto editRequestDto
    ) {
        editFacade.updateEdit(draftId, editRequestDto);

        return draftFacade.getDraft(draftId);
    }

    @NeedLogin
    @Operation(summary = "등록된 Draft 편집 자막 정보 수정")
    @PutMapping(value = "/draft/{draftId}/edit/subtitle")
    public DraftResponseDto updateEditSubtitle(
            @PathVariable("draftId") final Long draftId,
            @RequestBody EditSubtitleUpdateRequestDto editSubtitleUpdateRequestDto
    ) {
        editFacade.updateEditSubtitle(draftId, editSubtitleUpdateRequestDto);

        return draftFacade.getDraft(draftId);
    }

    @NeedLogin
    @Operation(summary = "등록된 비디오로 숏폼 생성")
    @PostMapping(value = "/draft/{draftId}/shorts")
    public ShortsResponseDto createVideoToShorts(@PathVariable("draftId") final Long draftId) {
        final long userId = AuthUtil.getCurrentUserId();

        return shortsFacade.createShorts(userId, draftId);
    }

    @NeedLogin
    @Operation(summary = "생성된 숏폼 데이터 목록 조회")
    @GetMapping(value = "/shorts-list")
    public List<ShortsListResponseDto> getShortsList() {
        final long userId = AuthUtil.getCurrentUserId();

        return shortsFacade.getShortsList(userId);
    }

    @NeedLogin
    @Operation(summary = "생성된 숏폼 데이터 단건 조회")
    @GetMapping(value = "/shorts/{shortsId}")
    public ShortsResponseDto getShorts(@PathVariable("shortsId") final long shortsId) {
        return shortsFacade.getShorts(shortsId);
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
