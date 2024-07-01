package com.swmaestro.cotuber.api.user;

import com.swmaestro.cotuber.common.ResponseMessage;
import com.swmaestro.cotuber.domain.user.dto.UserResponseDto;
import com.swmaestro.cotuber.domain.user.dto.UserUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 관련 API")
@RequiredArgsConstructor
@RestController
public class UserController {
    private static final String SUCCESS = "success";

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/users/info")
    public ResponseEntity<UserResponseDto> getUserInfo() {
        final UserResponseDto response = new UserResponseDto(
                "test1234@soma.com",
                "testuser",
                "http://test.com/image.jpg",
                "test_instagram",
                "test_tiktok"
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "유저 정보 수정")
    @PostMapping("/users/info")
    public ResponseEntity<ResponseMessage> updateUserInfo(@RequestBody UserUpdateRequestDto request) {
        final ResponseMessage responseMessage = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(responseMessage);
    }
}
