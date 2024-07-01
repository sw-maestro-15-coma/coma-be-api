package com.swmaestro.cotuber.api.auth;

import com.swmaestro.cotuber.common.ResponseMessage;
import com.swmaestro.cotuber.domain.user.dto.UserJoinRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
    2024-07-01: 논의 필요
*/
@Tag(name = "Auth", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private static final String SUCCESS = "success";

    @Operation(summary = "구글 로그인")
    @PostMapping("/auth/login")
    public ResponseEntity<ResponseMessage> login() {
        final ResponseMessage message = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/auth/logout")
    public ResponseEntity<ResponseMessage> logout() {
        final ResponseMessage message = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/auth/join")
    public ResponseEntity<ResponseMessage> join(@RequestBody UserJoinRequestDto request) {
        final ResponseMessage message = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/auth/resign")
    public ResponseEntity<ResponseMessage> resign() {
        final ResponseMessage message = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "토큰 갱신")
    @PostMapping("/auth/reissue")
    public ResponseEntity<ResponseMessage> reissue() {
        final ResponseMessage message = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(message);
    }
}
