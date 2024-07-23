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
        System.out.println("cookies : " + Arrays.toString(request.getCookies()));
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
        System.out.println(authentication);
        System.out.println(authentication.getName());

        if (!authentication.isAuthenticated()) {
            System.out.println("not authenticated");
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        if (authentication.getName() == null) {
            System.out.println("no name");
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            System.out.println("anonymous");
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}
