package com.swmaestro.cotuber.token;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.DateUtil.toLocalDateTime;


public record RefreshToken(
        long userId,
        String token,
        LocalDateTime expiresAt
) {
    public static RefreshToken from(long userId, String refreshToken, long expiresIn) {
        return new RefreshToken(
                userId,
                refreshToken,
                toLocalDateTime(expiresIn)
        );
    }
}
