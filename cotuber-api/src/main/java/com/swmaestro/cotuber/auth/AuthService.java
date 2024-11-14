package com.swmaestro.cotuber.auth;

import com.swmaestro.cotuber.TokenCreator;
import com.swmaestro.cotuber.TokenInfo;
import com.swmaestro.cotuber.token.RefreshTokenService;
import com.swmaestro.cotuber.user.User;
import com.swmaestro.cotuber.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManagerBuilder authManagerBuilder;
    private final RefreshTokenService refreshTokenService;
    private final UserReader userReader;
    private final TokenCreator tokenCreator;

    public Authentication getAuthentication(User user) {
        var authToken = new UsernamePasswordAuthenticationToken(String.valueOf(user.getId()), user.getOAuthId());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        return authManagerBuilder.getObject().authenticate(authToken);
    }

    public boolean shouldReissueToken(Optional<String> accessToken, Optional<String> refreshToken) {
        // refreshToken이 없거나 유효하지 않다면 재발급 대상이 아님
        if (refreshToken.isEmpty() || !tokenCreator.isValidToken(refreshToken.get())) {
            return false;
        }

        // 미로그인이거나 accessToken이 멀쩡할 때에는 굳이 재발급할 필요가 없음
        if (accessToken.isEmpty() || tokenCreator.isValidToken(accessToken.get())) {
            return false;
        }

        long userId = tokenCreator.getSubject(refreshToken.get());

        // refreshToken이 DB의 값과 같다면 재발급 대상
        return refreshTokenService.isRefreshTokenExists(userId, refreshToken.get(), LocalDateTime.now());
    }

    public TokenInfo reissueToken(String refreshToken) {
        long userId = tokenCreator.getSubject(refreshToken);
//        check(!refreshTokenService.isExist(userId, refreshoken)) { "로그아웃 된 사용자입니다." }

        User user = userReader.findById(userId);

        var authentication = getAuthentication(user);
        var tokenInfo = tokenCreator.generateToken(authentication);

        refreshTokenService.save(user, tokenInfo.refreshToken(), tokenInfo.refreshTokenExpiresIn());

        return tokenInfo;
    }
}
