package com.swmaestro.cotuber.config.constants;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "local-real"})
@Component
public class LocalTokenConstants implements TokenConstants {
    @Override
    public String getAccessTokenCookieName() {
        return "accessToken";
    }

    @Override
    public String getRefreshTokenCookieName() {
        return "refreshToken";
    }

    @Override
    public String getDomain() {
        return "localhost";
    }

    @Override
    public String getCookiePath() {
        return "/";
    }
}
