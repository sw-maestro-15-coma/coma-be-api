package com.swmaestro.cotuber.config.filter;

import com.swmaestro.cotuber.TokenInfo;
import com.swmaestro.cotuber.auth.AuthService;
import com.swmaestro.cotuber.config.MutableCookieRequestWrapper;
import com.swmaestro.cotuber.config.constants.TokenConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class ReissueTokenFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final TokenConstants tokenConstants;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        Optional<String> accessToken = getCookie(request, tokenConstants.getAccessTokenCookieName());
        Optional<String> refreshToken = getCookie(request, tokenConstants.getRefreshTokenCookieName());

        if (!authService.shouldReissueToken(accessToken, refreshToken)) {
            chain.doFilter(request, response);
            return;
        }

        TokenInfo token = authService.reissueToken(refreshToken.get());

        Cookie newAccessTokenCookie = makeCookie(
                tokenConstants.getAccessTokenCookieName(),
                token.accessToken(),
                token.refreshTokenExpiresIn()
        );

        Cookie newRefreshTokenCookie = makeCookie(
                tokenConstants.getRefreshTokenCookieName(),
                token.refreshToken(),
                token.refreshTokenExpiresIn()
        );

        var mutableCookieRequest = new MutableCookieRequestWrapper(request);
        mutableCookieRequest.replaceCookie(newAccessTokenCookie, tokenConstants.getAccessTokenCookieName());

        response.addCookie(newAccessTokenCookie);
        response.addCookie(newRefreshTokenCookie);

        chain.doFilter(mutableCookieRequest, response);
    }

    private Optional<String> getCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst();
    }

    private Cookie makeCookie(String name, String value, long expiresIn) {
        Cookie cookie = new Cookie(name, value);

        cookie.setMaxAge((int) (expiresIn / 1000));
        cookie.setDomain(tokenConstants.getDomain());
        cookie.setPath(tokenConstants.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        return cookie;
    }
}
