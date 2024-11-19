package com.swmaestro.cotuber.token;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RefreshTokenRepository {
    boolean isRefreshTokenExists(long userId, String token, LocalDateTime now);

    RefreshToken save(RefreshToken token);
}
