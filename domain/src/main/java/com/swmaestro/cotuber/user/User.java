package com.swmaestro.cotuber.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private final long id;
    private final String email;
    private final String oAuthId;
    private final String nickname;
    private final String profileImageUrl;
    private final AuthRole role;
    private String instagramId;
    private String tiktokId;

    @Builder
    public User(
            long id,
            String email,
            String oAuthId,
            String nickname,
            String profileImageUrl,
            AuthRole role,
            String instagramId,
            String tiktokId
    ) {
        this.id = id;
        this.email = email;
        this.oAuthId = oAuthId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.instagramId = instagramId;
        this.tiktokId = tiktokId;
    }

    public static User of(OAuthUserInfo userInfo) {
        return User.builder()
                .email(userInfo.email())
                .oAuthId(userInfo.oAuthId())
                .nickname(userInfo.nickname())
                .profileImageUrl(userInfo.profileImageUrl())
                .role(AuthRole.USER)
                .build();
    }
}
