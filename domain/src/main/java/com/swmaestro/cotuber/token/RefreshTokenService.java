package com.swmaestro.cotuber.token;

import com.swmaestro.cotuber.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.DateUtil.toTimestamp;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public boolean isRefreshTokenExists(long userId, String token, LocalDateTime now) {
        return refreshTokenRepository.isRefreshTokenExists(userId, token, toTimestamp(now));
    }

    public void save(User user, String refreshToken, long expiresIn) {
        RefreshToken token = RefreshToken.from(user.getId(), refreshToken, expiresIn);

        refreshTokenRepository.save(token);
    }
}

