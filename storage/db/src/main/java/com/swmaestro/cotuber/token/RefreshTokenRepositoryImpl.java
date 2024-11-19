package com.swmaestro.cotuber.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository refreshTokenRepository;

    @Override
    public boolean isRefreshTokenExists(long userId, String token, LocalDateTime now) {
        return refreshTokenRepository.isRefreshTokenExists(userId, token, now);
    }

    @Transactional
    @Override
    public RefreshToken save(RefreshToken token) {
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .userId(token.userId())
                .token(token.token())
                .expiresAt(token.expiresAt())
                .build();

        refreshTokenRepository.save(refreshToken);

        return new RefreshToken(
                refreshToken.getUserId(),
                refreshToken.getToken(),
                refreshToken.getExpiresAt()
        );
    }
}
