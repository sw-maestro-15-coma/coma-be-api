package com.swmaestro.cotuber.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API")
@RequiredArgsConstructor
@RestController
public class AuthController {
    @Operation(summary = "구글 로그인")
    @PostMapping("/auth/login")
    public void login() {
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/auth/logout")
    public void logout() {
    }

    @Operation(summary = "회원가입")
    @PostMapping("/auth/join")
    public void join() {
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/auth/resign")
    public void resign() {
    }

    @Operation(summary = "토큰 갱신")
    @PostMapping("/auth/reissue")
    public void reissue() {
    }
}
