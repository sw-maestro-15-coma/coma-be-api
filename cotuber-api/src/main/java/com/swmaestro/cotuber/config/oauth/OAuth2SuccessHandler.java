package com.swmaestro.cotuber.config.oauth;

import com.swmaestro.cotuber.TokenCreator;
import com.swmaestro.cotuber.TokenInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

        response.addHeader(HttpHeaders.AUTHORIZATION, token.accessToken());
        response.addHeader(HttpHeaders.AUTHORIZATION + "Refresh", token.refreshToken());

        String redirectUrl = getRedirectUrl(request);

        if (redirectUrl.equals("popup")) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>window.onload = () => { window.opener?.location?.reload?.(); window.close(); }</script>");
            response.getWriter().flush();
        } else {
            response.sendRedirect(getRedirectUrl(request));
        }
    }

    private String getRedirectUrl(HttpServletRequest request) {
        String redirectUrl = request.getParameter("state");

        if (redirectUrl == null) {
            return DEFAULT_REDIRECT_URL;
        }

        return redirectUrl;
    }
}

