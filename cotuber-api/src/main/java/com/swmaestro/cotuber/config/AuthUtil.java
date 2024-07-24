package com.swmaestro.cotuber.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtil {
    public static String getAccessToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("accessToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public static long getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        if (authentication.getName() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return Long.parseLong(authentication.getName());
    }

    public static Long getCurrentNullishUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return null;
        }

        if (authentication.getName() == null) {
            return null;
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return Long.parseLong(authentication.getName());
    }
}
