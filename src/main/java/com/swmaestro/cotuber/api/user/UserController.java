package com.swmaestro.cotuber.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 관련 API")
@RequiredArgsConstructor
@RestController
public class UserController {
    @Operation(summary = "유저 정보 조회")
    @GetMapping("/users/info")
    public void getUserInfo() {
    }

    @Operation(summary = "유저 정보 수정")
    @PostMapping("/users/info")
    public void updateUserInfo() {
    }
}
