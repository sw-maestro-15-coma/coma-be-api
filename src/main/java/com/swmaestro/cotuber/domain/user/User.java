package com.swmaestro.cotuber.domain.user;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "account ")
public class User extends BaseEntity {
    @Column(name = "email", length = 30, nullable = false)
    private String email;
    @Column(name = "oauth_id", length = 30, nullable = false)
    private String oAuthId;
    @Column(name = "nickname", length = 30, nullable = false)
    private String nickname;
    @Column(name = "profile_image_url", length = 100)
    private String profileImageUrl;
    @Column(name = "instagram_id", length = 20)
    private String instagramId;
    @Column(name = "tiktok_id", length = 20)
    private String tiktokId;
}
