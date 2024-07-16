package com.swmaestro.cotuber.token;

import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository {
    boolean isRefreshTokenExists(long userId, String token, long now);

    RefreshToken save(RefreshToken token);
}
