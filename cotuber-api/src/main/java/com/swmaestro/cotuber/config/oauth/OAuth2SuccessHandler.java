package com.swmaestro.cotuber.config.oauth;

import com.swmaestro.cotuber.TokenCreator;
import com.swmaestro.cotuber.TokenInfo;
import com.swmaestro.cotuber.config.constants.TokenConstants;
import com.swmaestro.cotuber.token.RefreshTokenService;
import com.swmaestro.cotuber.user.User;
import com.swmaestro.cotuber.user.UserReader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private static final String DEFAULT_REDIRECT_URL = "https://cotuber.com";
    private final TokenCreator tokenCreator;
    private final TokenConstants tokenConstants;
    private final RefreshTokenService refreshTokenService;
    private final UserReader userReader;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        TokenInfo token = tokenCreator.generateToken(authentication);
        
        long userId = tokenCreator.getSubject(token.accessToken());
        User user = userReader.findById(userId);

        refreshTokenService.save(user, token.refreshToken(), token.refreshTokenExpiresIn());

        // 토큰 전달을 위한 redirect
        response.addHeader(HttpHeaders.SET_COOKIE, createAccessTokenCookie(token).toString());
        response.addHeader(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(token).toString());

        response.sendRedirect(getRedirectUrl(request));
    }

    private ResponseCookie createAccessTokenCookie(TokenInfo tokenInfo) {
        return ResponseCookie.from(tokenConstants.getAccessTokenCookieName(), tokenInfo.accessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .domain(tokenConstants.getDomain())
                .path(tokenConstants.getCookiePath())
                .maxAge(tokenInfo.accessTokenExpiresIn())
                .build();
    }

    private ResponseCookie createRefreshTokenCookie(TokenInfo token) {
        return ResponseCookie.from(tokenConstants.getRefreshTokenCookieName(), token.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .domain(tokenConstants.getDomain())
                .path(tokenConstants.getCookiePath())
                .maxAge(token.refreshTokenExpiresIn())
                .build();
    }

    private String getRedirectUrl(HttpServletRequest request) {
        String redirectUrl = request.getParameter("state");

        if (redirectUrl == null) {
            return DEFAULT_REDIRECT_URL;
        }

        return redirectUrl;
    }
}
