package com.swmaestro.cotuber.config.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {
    private static final String DEFAULT_REDIRECT_URL = "https://cotuber.com";

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        response.sendRedirect(getRedirectUrl(request));
    }

    private String getRedirectUrl(HttpServletRequest request) {
        String redirectUrl = request.getParameter("state");

        if (redirectUrl == null) {
            return DEFAULT_REDIRECT_URL;
        }

        return redirectUrl;
    }
}
