package com.swmaestro.cotuber.api;

import com.swmaestro.cotuber.user.User;

public record UserInfoResponseDto(
        boolean isLoggedIn,
        UserInfo user
) {
    record UserInfo(
            String nickname,
            String grade,
            String profileImageUrl
    ) {
    }

    public static UserInfoResponseDto loggedIn(User user) {
        return new UserInfoResponseDto(
                true,
                new UserInfo(
                        user.getNickname(),
                        "PLATINUM",
                        user.getProfileImageUrl()
                )
        );
    }

    public static UserInfoResponseDto notLoggedIn() {
        return new UserInfoResponseDto(false, null);
    }
}
