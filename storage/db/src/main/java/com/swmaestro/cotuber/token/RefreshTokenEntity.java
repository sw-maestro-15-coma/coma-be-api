package com.swmaestro.cotuber.token;

import com.swmaestro.cotuber.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_token")
@Entity
public class RefreshTokenEntity extends BaseEntity {
    @Column(name = "user_id", nullable = false, updatable = false)
    private long userId;

    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;
}
