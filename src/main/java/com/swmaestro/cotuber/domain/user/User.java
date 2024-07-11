package com.swmaestro.cotuber.domain.user;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @Column(name = "email", length = 30, nullable = false)
    private String email;
    @Column(name = "oauth_id", length = 30, nullable = false)
    private String oAuthId;
    @Column(name = "nickname", length = 30, nullable = false)
    private String nickname;
    @Column(name = "profile_image_url", length = 100)
    private String profileImageUrl;
    private AuthRole role;
    @Column(name = "instagram_id", length = 20)
    private String instagramId;
    @Column(name = "tiktok_id", length = 20)
    private String tiktokId;

    @Builder
    public User(String email, String oAuthId, String nickname, String profileImageUrl) {
        this.email = email;
        this.oAuthId = oAuthId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = AuthRole.USER;
    }

    public static User of(OAuthUserInfo userInfo) {
        return User.builder()
                .email(userInfo.email())
                .oAuthId(userInfo.oAuthId())
                .nickname(userInfo.nickname())
                .profileImageUrl(userInfo.profileImageUrl())
                .build();
    }
}
