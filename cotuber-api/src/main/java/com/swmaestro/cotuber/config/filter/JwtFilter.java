package com.swmaestro.cotuber.config.filter;

import com.swmaestro.cotuber.TokenCreator;
import com.swmaestro.cotuber.auth.AuthService;
import com.swmaestro.cotuber.user.User;
import com.swmaestro.cotuber.user.UserReader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.swmaestro.cotuber.config.AuthUtil.getAccessToken;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserReader userReader;
    private final AuthService authService;
    private final TokenCreator tokenCreator;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getAccessToken(request);

        if (tokenCreator.isValidToken(token)) {
            long userId = tokenCreator.getSubject(token);
            User user = userReader.findById(userId);

            var authentication = authService.getAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}

