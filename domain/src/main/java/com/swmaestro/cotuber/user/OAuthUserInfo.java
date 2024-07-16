package com.swmaestro.cotuber.user;

import lombok.Builder;

@Builder
public record OAuthUserInfo(
        String oAuthId,
        String email,
        String nickname,
        String profileImageUrl
) {
}
