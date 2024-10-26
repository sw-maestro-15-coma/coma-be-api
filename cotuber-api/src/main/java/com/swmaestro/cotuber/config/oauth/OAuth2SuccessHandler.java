package com.swmaestro.cotuber.config.oauth;

import com.swmaestro.cotuber.TokenCreator;
import com.swmaestro.cotuber.TokenInfo;
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

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        TokenInfo token = tokenCreator.generateToken(authentication);

        // 토큰 전달을 위한 redirect
        response.addHeader(HttpHeaders.SET_COOKIE, createAccessTokenCookie(token).toString());
        response.addHeader(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(token).toString());

        response.sendRedirect(getRedirectUrl(request));
    }

    private ResponseCookie createAccessTokenCookie(TokenInfo tokenInfo) {
        return ResponseCookie.from("accessToken", tokenInfo.accessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .domain("www.cotuber.com")  // TODO: 토큰 주입으로 변경
                .path("/")
                .maxAge(tokenInfo.accessTokenExpiresIn())
                .build();
    }

    private ResponseCookie createRefreshTokenCookie(TokenInfo token) {
        return ResponseCookie.from("refreshToken", token.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .domain("www.cotuber.com") // TODO: 토큰 주입으로 변경
                .path("/")
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
