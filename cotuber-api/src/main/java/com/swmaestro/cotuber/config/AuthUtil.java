package com.swmaestro.cotuber.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtil {
    public static String getBearerToken(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearer == null) {
            return null;
        }

        return bearer.split("Bearer ")[1];
    }

    public static long getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        if (authentication.getName() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        if (authentication.getName().equals("anonymousUser")) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}
