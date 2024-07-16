package com.swmaestro.cotuber;

import lombok.Builder;

@Builder
public record TokenInfo(
        String accessToken,
        String refreshToken,
        long accessTokenExpiresIn,
        long refreshTokenExpiresIn
) {
}
