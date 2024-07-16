package com.swmaestro.cotuber.user;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity extends BaseEntity {
    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @Column(name = "oauth_id", length = 30, nullable = false)
    private String oAuthId;

    @Column(name = "nickname", length = 30, nullable = false)
    private String nickname;

    @Column(name = "profile_image_url", length = 100)
    private String profileImageUrl;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthRole role;

    @Column(name = "instagram_id", length = 20)
    private String instagramId;

    @Column(name = "tiktok_id", length = 20)
    private String tiktokId;

    @Builder
    public UserEntity(
            long id,
            String email,
            String oAuthId,
            String nickname,
            String profileImageUrl,
            AuthRole role,
            String instagramId,
            String tiktokId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        super(id, createdAt, updatedAt);
        this.email = email;
        this.oAuthId = oAuthId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.instagramId = instagramId;
        this.tiktokId = tiktokId;
    }

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .oAuthId(user.getOAuthId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .instagramId(user.getInstagramId())
                .tiktokId(user.getTiktokId())
                .build();
    }

    public User toDomain() {
        return User.builder()
                .id(getId())
                .email(email)
                .oAuthId(oAuthId)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .role(role)
                .instagramId(instagramId)
                .tiktokId(tiktokId)
                .build();
    }
}