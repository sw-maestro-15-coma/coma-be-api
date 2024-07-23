package com.swmaestro.cotuber.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final OAuth2AuthorizationRequestResolver defaultResolver;

    public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository,
                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
        );
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        var authorizationRequest = defaultResolver.resolve(request);
        return customAuthorizationRequest(request, authorizationRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        var authorizationRequest = defaultResolver.resolve(request, clientRegistrationId);
        return customAuthorizationRequest(request, authorizationRequest);
    }

    private OAuth2AuthorizationRequest customAuthorizationRequest(
            HttpServletRequest request,
            OAuth2AuthorizationRequest authorizationRequest
    ) {
            if (authorizationRequest == null) {
            return null;
        }

        String redirectUrl = request.getParameter("redirect_url");

        if (redirectUrl == null) {
            return authorizationRequest;
        }

        return OAuth2AuthorizationRequest.from(authorizationRequest)
                .state(redirectUrl)
                .build();
    }
}
