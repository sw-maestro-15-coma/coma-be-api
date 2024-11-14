package com.swmaestro.cotuber.config.constants;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("real")
@Component
public class RealTokenConstants implements TokenConstants {
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
        return "cotuber.com";
    }

    @Override
    public String getCookiePath() {
        return "/";
    }
}
